package org.ubis.ubis.domain.review.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.ubis.ubis.domain.review.dto.*
import org.ubis.ubis.domain.review.service.ReviewService

@RequestMapping("/products/{productId}/reviews")
@RestController
class ReviewController(
    private val reviewService : ReviewService
) {

    @PostMapping
    fun createReview(
        @PathVariable productId: Long,
        @RequestBody request: ReviewRequest,
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(productId, request))
    }

    @GetMapping
    fun getReviewList(
        @PathVariable productId: Long,
    ):ResponseEntity<List<ReviewResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.getReviewList(productId))
    }

    @PutMapping("/{reviewId}")
    fun updateReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
        @RequestBody request: ReviewRequest
    ):ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.updateReview(reviewId, request))
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
    ):ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(reviewService.deleteReview(reviewId))
    }
}
