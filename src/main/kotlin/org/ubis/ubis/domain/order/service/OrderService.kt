package org.ubis.ubis.domain.order.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.member.model.Member
import org.ubis.ubis.domain.member.model.Role
import org.ubis.ubis.domain.member.repository.MemberRepository
import org.ubis.ubis.domain.member.service.MemberService
import org.ubis.ubis.domain.order.dto.OrderResponse
import org.ubis.ubis.domain.order.model.Order
import org.ubis.ubis.domain.order.model.toOrderResponse
import org.ubis.ubis.domain.order.repository.OrderRepository
import org.ubis.ubis.domain.product.repository.ProductRepository

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val memberService: MemberService,
    private val memberRepository: MemberRepository
) {

    fun getOrderList(): List<OrderResponse> {
        val memberId = memberService.getMemberIdFromToken()!!
        var orders = mutableListOf<OrderResponse>()
        when(memberService.getMember().role) {
            Role.CUSTOMER -> {//함수화
                val memberName = memberService.getMember().name!!
                orders = orderRepository.findAllByMemberId(memberId)
                    .map { it.toOrderResponse(memberName) }
                    .toMutableList()
            }

            Role.BUSINESS -> {//함수화
                orders = orderRepository.findOrderList(memberId).map {
                    it.first!!.toOrderResponse(it.second!!)
                }.toMutableList()
            }
        }
        return orders
    }

    fun getOrder(productId: Long, orderId: Long): OrderResponse {
        val result = orderRepository.findByProductIdAndId(productId, orderId)
            ?: throw RuntimeException("Product with ID $orderId not found")
        val member = memberRepository.findById(result.memberId) as Member
        return result.toOrderResponse(member.name)
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
        val memberName = memberRepository.findByIdOrNull(order.memberId)!!.name
        return order.toOrderResponse(memberName)
    }

    @Transactional
    fun deleteOrder(productId: Long, orderId: Long) {
        val product = productRepository.findByIdOrNull(productId)
            ?: throw RuntimeException("Product with ID $productId not found")
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw RuntimeException("Order with ID $orderId not found")

        val isGood= when( memberService.getMember().role){
            Role.CUSTOMER -> {
                memberService.matchMemberId(order.memberId)
            }
            Role.BUSINESS -> {
                memberService.matchMemberId(product.memberId)
            }
        }
        if(!isGood) throw RuntimeException("권한없음")

        product.deleteOrder(order)
        productRepository.save(product)
    }
}
