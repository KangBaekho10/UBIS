package org.ubis.ubis.domain.review.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.Tuple
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

        //1. 프로덕트 id로 where
        //2. 멤버 Id를 통해 memberTable에 inner join
        //3. 조인된 member에서 name을 추출
        //4. 리뷰 엔티티에 같이 씌우기..?(디비에서 추가하지 않고, 엔티티에서 추가)
        //5. 이것을 리턴

        //실행할때 멤버 테이블을 핸들링


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