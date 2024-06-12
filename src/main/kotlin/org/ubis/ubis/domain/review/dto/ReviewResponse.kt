package org.ubis.ubis.domain.review.dto

data class ReviewResponse(
    val id : Long,
    val content: String,
    val writer:String,
    val createdAt: String,
)
