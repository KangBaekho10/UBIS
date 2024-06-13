package org.ubis.ubis.domain.order.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.member.model.Role
import org.ubis.ubis.domain.member.service.MemberService
import org.ubis.ubis.domain.order.dto.OrderResponse
import org.ubis.ubis.domain.order.model.Order
import org.ubis.ubis.domain.order.model.toOrderResponse
import org.ubis.ubis.domain.order.repository.OrderRepository
import org.ubis.ubis.domain.product.model.Product
import org.ubis.ubis.domain.product.repository.ProductRepository

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val memberService: MemberService
) {
    private val isBusiness: () ->Boolean = {
        memberService.getMember().role == Role.BUSINESS }
    private val isCustomer: () ->Boolean = {
        memberService.getMember().role == Role.CUSTOMER }

    fun getOrderList(): List<OrderResponse> {
        val memberId = memberService.getMemberIdFromToken()!!
        var orders = mutableListOf<OrderResponse>()
        if (isBusiness()) {
            productRepository.findByMemberId(memberId)
                .forEach { product ->
                    orderRepository.findAllByProductId(product.id!!)
                        .forEach { order ->
                            orders.add(order.toOrderResponse())
                        }
                }
        } else if (isCustomer()) {
            orders = orderRepository.findAllByMemberId(memberId)
                .map { it.toOrderResponse() }
                .toMutableList()
        }
        return orders
    }

    fun getOrder(productId: Long, orderId: Long): OrderResponse {
        val result = orderRepository.findByProductIdAndId(productId, orderId)
            ?: throw RuntimeException("Product with ID $orderId not found")
        return result.toOrderResponse()
    }

    fun existsOrder(productId: Long, memberId: Long): Boolean {
        return orderRepository.existsByProductIdAndMemberId(productId, memberId)
    }

    @Transactional
    fun createOrder(productId: Long): OrderResponse {
        val product =
            productRepository.findByIdOrNull(productId)
                ?: throw RuntimeException("Product with ID $productId not found")
        val order = Order(
            productPrice = product.price,
            product = product,
            memberId = memberService.getMemberIdFromToken()!!
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

        var isGood:Boolean=false
        if(isBusiness()){
            isGood=memberService.matchMemberId(product.memberId)
        }
        else if(isCustomer()){
            isGood=memberService.matchMemberId(order.memberId)
        }
        if(!isGood)
            throw RuntimeException("권한없음")
        product.deleteOrder(order)
        productRepository.save(product)
    }
}
