package org.ubis.ubis.domain.order.dto

import java.time.LocalDateTime

data class OrderResponse (
    val id: Long,
    val createdAt: LocalDateTime,
    val productPrice: Float
)