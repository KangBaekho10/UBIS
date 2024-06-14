package org.ubis.ubis.domain.order.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.ubis.ubis.domain.order.dto.OrderResponse
import org.ubis.ubis.domain.product.model.Product
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "product_price")
    var productPrice: Float,

    @Column(name = "member_id")
    var memberId: Long,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


}

fun Order.toOrderResponse(memberName: String): OrderResponse = OrderResponse(
    id = id!!,
    createdAt = createdAt,
    productPrice = productPrice,
    productName = product.name,
    memberName = memberName,
)