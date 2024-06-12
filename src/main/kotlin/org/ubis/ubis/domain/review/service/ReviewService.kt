package org.ubis.ubis.domain.review.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.review.dto.*
import org.ubis.ubis.domain.review.repository.ReviewRepository

@Service
class ReviewService(
    private val repository: ReviewRepository,
) {

    @Transactional
    fun createReview(productId:Long, request: ReviewRequest): ReviewResponse {
        return TODO(
            "프로덕트 엔티티 받아오기" +
                    "리뷰 엔티티 생성" +
                "리뷰 리포지토리로 세이브" +
                    "리뷰 toResponse")
    }

    fun getReviewList(productId:Long): List<ReviewResponse>{
        return TODO("프로덕트 엔티티 받아오기" +
                "프로덕트와 연관관계 리뷰 불러오기" +
                "map.toResponse")
    }

    @Transactional
    fun updateReview(reviewId:Long,request: ReviewRequest):Unit{
        return  repository.findByIdOrNull(reviewId)
            ?.let {
                it.content=request.content
            }?: throw EntityNotFoundException("not found")
    }

    @Transactional
    fun deleteReview(reviewId:Long, request: ReviewRequest){
        return TODO("리뷰 엔티티 받아오기" +
                "리뷰 삭제")
    }
}