package org.ubis.ubis.domain.review.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.product.service.ProductService
import org.ubis.ubis.domain.review.dto.*
import org.ubis.ubis.domain.review.model.Review
import org.ubis.ubis.domain.review.repository.ReviewRepository

@Service
class ReviewService(
    private val repository: ReviewRepository,
    private val productService: ProductService
) {

    @Transactional
    fun createReview(productId:Long, request: ReviewRequest): ReviewResponse {
        return productService.getProductEntity(productId)
            .let { repository.save(toEntity(it,request)) }
            .toResponse()
    }

    fun getReviewList(productId:Long): List<ReviewResponse>{
        return repository.findByProductId(productId)
            .map { it.toResponse() }
    }

    @Transactional
    fun updateReview(reviewId:Long,request: ReviewRequest):Unit{
        return  repository.findByIdOrNull(reviewId)
            ?.let {
                it.content=request.content
            }?: throw EntityNotFoundException("not found")
    }

    @Transactional
    fun deleteReview(reviewId:Long){
        return repository.deleteById(reviewId)
    }
}