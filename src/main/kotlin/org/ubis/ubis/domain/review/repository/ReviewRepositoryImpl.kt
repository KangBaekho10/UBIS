package org.ubis.ubis.domain.review.repository

import com.querydsl.core.BooleanBuilder
import org.ubis.ubis.domain.member.model.QMember.member
import org.ubis.ubis.domain.review.model.QReview.review
import org.ubis.ubis.domain.review.model.Review
import org.ubis.ubis.infra.querydsl.QueryDslSupport

class ReviewRepositoryImpl
    :ReviewRepositoryCustom
    ,QueryDslSupport() {
    override fun findMemberName(productId:Long,memberId: Long): MutableList<Pair<Review?,String?>> {
        val whereClause = BooleanBuilder()

        whereClause.and(review.product.id.eq(productId))

        val contents = queryFactory
            .select(review, member.name)
            .from(review)
            .where(whereClause)
            .join(member) // 연관 관계와 무관한 JOIN
            .on(review.memberId.eq(member.id)) // 리뷰의 멤버 ID와 멤버의 멤버 ID가 일치
            .fetch()

        return contents.map{
            it.get(review) to it.get(member.name)
        }.toMutableList()
    }
}