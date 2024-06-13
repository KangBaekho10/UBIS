package org.ubis.ubis.domain.product.dto

data class UpdateProductRequest(
    val name: String,
    val description: String,
    val price: Float,
    val imgs: String
)
