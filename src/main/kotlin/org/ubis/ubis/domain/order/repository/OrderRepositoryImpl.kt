package org.ubis.ubis.domain.order.repository

import com.querydsl.core.BooleanBuilder
import org.ubis.ubis.infra.querydsl.QueryDslSupport
import org.ubis.ubis.domain.product.model.QProduct.product
import org.ubis.ubis.domain.member.model.QMember.member
import org.ubis.ubis.domain.order.model.Order
import org.ubis.ubis.domain.order.model.QOrder.order

class OrderRepositoryImpl:OrderRepositoryCustom,QueryDslSupport() {
    override fun findOrderList(memberId: Long): MutableList<Pair<Order?,String?>> {
        val whereClause = BooleanBuilder()

        whereClause.and(product.memberId.eq(memberId))

        val contents = queryFactory
            .select(order, member.name)
            .from(order)
            .where(whereClause)
            .join(member) // 연관 관계와 무관한 JOIN
            .on(order.memberId.eq(member.id)) // 리뷰의 멤버 ID와 멤버의 멤버 ID가 일치
            .fetch()

        return contents.map{
            it.get(order) to it.get(member.name)
        }.toMutableList()
    }
}