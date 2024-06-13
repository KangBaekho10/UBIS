package org.ubis.ubis.domain.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ubis.ubis.domain.order.model.Order

interface OrderRepository: JpaRepository<Order, Long> {
    fun findByProductIdAndId(productId: Long, orderId: Long): Order?
    fun existsByProductIdAndMemberId(productId: Long, memberId:Long): Boolean
    fun findAllByMemberId(memberId: Long): List<Order>
    fun findAllByProductId(productId: Long): List<Order>
}