package org.ubis.ubis.domain.review.service

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

