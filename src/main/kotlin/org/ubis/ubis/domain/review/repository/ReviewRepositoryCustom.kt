package org.ubis.ubis.domain.review.repository

import org.ubis.ubis.domain.review.model.Review

interface ReviewRepositoryCustom {
    fun findMemberName(productId:Long,memberId: Long): MutableList<Pair<Review?,String?>>
}