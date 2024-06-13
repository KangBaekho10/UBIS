package org.ubis.ubis.domain.product.model

import jakarta.persistence.*
import org.ubis.ubis.domain.order.model.Order
import org.ubis.ubis.domain.product.dto.ProductResponse
import java.time.LocalDateTime

@Entity
@Table(name = "product")
class Product(

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "price")
    var price: Float,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "imgs")
    var imgs: String,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var orders: MutableList<Order> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun createOrder(order: Order) {
        orders.add(order)
    }

    fun deleteOrder(order: Order) {
        orders.remove(order)
    }
}

fun Product.toProductResponse(): ProductResponse = ProductResponse(
    id = id!!,
    name = name,
    description = description,
    price = price,
    createdAt = createdAt,
    imgs = imgs
)