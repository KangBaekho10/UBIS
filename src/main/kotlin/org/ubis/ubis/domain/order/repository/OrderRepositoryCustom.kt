package org.ubis.ubis.domain.order.repository

import org.ubis.ubis.domain.order.model.Order

interface OrderRepositoryCustom {
    fun findOrderList(memberId: Long): MutableList<Pair<Order?,String?>>
}