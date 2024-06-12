package org.ubis.ubis.domain.review.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.ubis.ubis.domain.review.dto.*
import org.ubis.ubis.domain.review.service.ReviewService

@RestController
class ReviewController(
    private val reviewService : ReviewService
) {

    @PostMapping("/products/{productId}/reviews")
    fun createReview(
        @PathVariable productId: Long,
        @RequestBody request: ReviewRequest,
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(productId, request))
    }

    @GetMapping("/products/{productId}/reviews")
    fun getReviewList(
        @PathVariable productId: Long,
    ):ResponseEntity<List<ReviewResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.getReviewList(productId))
    }

    @PutMapping("/reviews/{reviewId}")
    fun updateReview(
        @PathVariable reviewId: Long,
        @RequestBody request: ReviewRequest
    ):ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.updateReview(reviewId, request))
    }

    @DeleteMapping("/reviews/{reviewId}")
    fun deleteReview(
        @PathVariable reviewId: Long,
    ):ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(reviewService.deleteReview(reviewId))
    }
}
