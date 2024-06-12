package org.ubis.ubis.domain.order.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.order.dto.CreateOrderRequest
import org.ubis.ubis.domain.order.dto.OrderResponse
import org.ubis.ubis.domain.order.model.Order
import org.ubis.ubis.domain.order.model.toOrderResponse
import org.ubis.ubis.domain.order.repository.OrderRepository
import org.ubis.ubis.domain.product.repository.ProductRepository

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {
    fun getOrderList(): List<OrderResponse> {
        return orderRepository.findAll().map { it.toOrderResponse() }
    }

    fun getOrder(productId: Long, orderId: Long): OrderResponse {
        val result = orderRepository.findByProductIdAndId(productId, orderId)
            ?: throw RuntimeException("Product with ID $orderId not found")
        return result.toOrderResponse()
    }

    @Transactional
    fun createOrder(productId: Long, request: CreateOrderRequest): OrderResponse {
        val product =
            productRepository.findByIdOrNull(productId)
                ?: throw RuntimeException("Product with ID $productId not found")
        val order = Order(
            productPrice = request.productPrice,
            product = product
        )
        product.createOrder(order)
        orderRepository.save(order)
        return order.toOrderResponse()
    }

    @Transactional
    fun deleteOrder(productId: Long, orderId: Long) {
        val product = productRepository.findByIdOrNull(productId)
            ?: throw RuntimeException("Product with ID $productId not found")
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw RuntimeException("Order with ID $orderId not found")

        product.deleteOrder(order)
        productRepository.save(product)
    }
}
