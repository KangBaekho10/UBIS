package org.ubis.ubis.domain.review.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.member.model.Member
import org.ubis.ubis.domain.member.repository.MemberRepository
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
    private val memberRepository: MemberRepository,
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

    // 1. 리뷰에 작성자 추가
    // 2. for문으로 쿼리를 다 불러오기
    // 3. membertable 다 불러와서 filter
    // 4. querydsl로 기똥차게
    // 튜터팀 추천
    // 1. 멤버 테이블을 미리 가지고 있자!
    // 2. querydsl추천 (3,4번 같이 쓴다고 생각해볼 수 있다)


    fun getReviewList(productId:Long): List<ReviewResponse>{
//        return repository.findByProductId(productId)
//            .map { it.toResponse(memberService.it.memberId) }
        val reviewList = repository.findByProductId(productId)
//        val memberName = (memberRepository.findById(reviewList[0].memberId) as Member).n
        val memberName = memberRepository.findByIdOrNull(reviewList[0].memberId)!!.name

        val result=repository.findMemberName(productId, memberService.getMemberIdFromToken()!!)
        return result.map {
            it.first!!.toResponse(it.second.toString())
        }
//        val resultFirstValue = result?.get(0)?.first
//        val resultSecondValue = result?.get(0)?.second
        //return reviewList.map { it.toResponse(memberName) }
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