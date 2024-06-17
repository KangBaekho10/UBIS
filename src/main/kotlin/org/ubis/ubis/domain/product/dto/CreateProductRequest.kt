package org.ubis.ubis.domain.product.dto

data class CreateProductRequest (
    val name: String,
    val description: String,
    val price: Float,
    val imgs: String
)