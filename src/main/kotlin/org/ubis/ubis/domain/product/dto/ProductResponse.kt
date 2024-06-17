package org.ubis.ubis.domain.product.dto

import java.time.LocalDateTime

data class ProductResponse (
    val id: Long,
    val name: String,
    val description: String,
    val price: Float,
    val createdAt: LocalDateTime,
    val imgs: String
)