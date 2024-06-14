package org.ubis.ubis.domain.review.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.member.service.MemberService
import org.ubis.ubis.domain.order.service.OrderService
import org.ubis.ubis.domain.product.service.ProductService
import org.ubis.ubis.domain.review.dto.*
import org.ubis.ubis.domain.review.repository.ReviewRepository

@Service
class ReviewService(
    private val repository: ReviewRepository,
    private val productService: ProductService,
    private val memberService: MemberService,
    private val orderService: OrderService,
) {

    @Transactional
    fun createReview(productId:Long, request: ReviewRequest): ReviewResponse {
        val member=memberService.getMember()
        if(!orderService.existsOrder(productId,member.id))
            throw RuntimeException("구매자가 아님")
        if(repository.existsByProductIdAndMemberId(productId,member.id))
            throw RuntimeException("중복리뷰 불가")
        return productService.getProductEntity(productId)
            .let { repository.save(toEntity(it,request,member.id)) }
            .toResponse(member.name)
    }

    fun getReviewList(productId:Long): List<ReviewResponse>{
        val result=repository.findMemberName(productId, memberService.getMemberIdFromToken()!!)
        return result.map {
            it.first!!.toResponse(it.second.toString())
        }
    }

    @Transactional
    fun updateReview(productId:Long, reviewId:Long,request: ReviewRequest):Unit{
        return  repository.findByIdOrNull(reviewId)
            ?.let {
                if(!memberService.matchMemberId(it.memberId))
                    throw RuntimeException("매칭되지 않았음")
                it.content=request.content
            }?: throw EntityNotFoundException("not found")
    }

    @Transactional
    fun deleteReview(productId:Long, reviewId:Long){
        return  repository.findByIdOrNull(reviewId)
            ?.let {
                if(!memberService.matchMemberId(it.memberId))
                    throw RuntimeException("매칭되지 않았음")
                repository.deleteById(reviewId)
            }?: throw EntityNotFoundException("not found")
    }
}