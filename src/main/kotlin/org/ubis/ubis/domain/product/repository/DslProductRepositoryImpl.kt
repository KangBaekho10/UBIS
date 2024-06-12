package org.ubis.ubis.domain.product.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.ubis.ubis.domain.product.model.Product
import org.ubis.ubis.domain.product.model.QProduct
import org.ubis.ubis.infra.querydsl.QueryDslSupport
import com.querydsl.core.types.Expression
import org.springframework.data.domain.PageImpl

@Repository
class DslProductRepositoryImpl
    :CustomProductRepository
    ,QueryDslSupport() {
    private val products = QProduct.product

    override fun findAllPageableOrder(pageable: Pageable)
    : Page<Product>
    {
        val totalCount = queryFactory
            .select(products.count())
            .from(products)
            .fetchOne() ?: 0L

        val contents = queryFactory
            .selectFrom(products)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, products))
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