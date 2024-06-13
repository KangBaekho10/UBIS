package org.ubis.ubis.domain.review.service

import org.ubis.ubis.domain.member.model.Role
import org.ubis.ubis.domain.product.model.Product
import org.ubis.ubis.domain.review.dto.ReviewRequest
import org.ubis.ubis.domain.review.dto.ReviewResponse
import org.ubis.ubis.domain.review.model.Review


fun Review.toResponse()
        : ReviewResponse {
    return ReviewResponse(
        id = id!!,
        content=content,
        writer = "",
        createdAt = createdAt.toString(),
    )
}

fun toEntity(
    product: Product, request: ReviewRequest): Review {
    return Review(
        content = request.content,
        product = product,
        memberId = 1000000
    )
}
