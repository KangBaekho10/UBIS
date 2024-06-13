package org.ubis.ubis.domain.product.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.ubis.ubis.domain.product.model.Product
import org.ubis.ubis.infra.querydsl.QueryDslSupport
import com.querydsl.core.types.Expression
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Repository
import org.ubis.ubis.domain.product.model.QProduct.*

@Repository
class ProductRepositoryImpl
    :ProductRepositoryCustom
    ,QueryDslSupport() {

    override fun findProductList(pageable: Pageable, name: String?)
    : Page<Product>
    {
        val whereClause = BooleanBuilder()

        name?.let { whereClause.and(product.name.like("%$name%")) }

        val totalCount = queryFactory
            .select(product.count())
            .from(product)
            .where(whereClause)
            .fetchOne() ?: 0L

        val contents = queryFactory
            .selectFrom(product)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, product))
            .fetch()

        // Page 구현체 반환
        return PageImpl(contents, pageable, totalCount)
    }

    private fun getOrderSpecifier(
        pageable: Pageable,
        path: EntityPathBase<*>)
            : Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if (order.isAscending) Order.ASC else Order.DESC,
                pathBuilder.get(order.property)
                        as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
}