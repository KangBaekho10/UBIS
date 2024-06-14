package org.ubis.ubis.domain.review.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ubis.ubis.domain.review.model.Review

interface ReviewRepository: JpaRepository<Review, Long>,ReviewRepositoryCustom {
    fun findByProductId(productId: Long): List<Review>
    fun existsByProductIdAndMemberId(productId: Long,memberId:Long): Boolean
}