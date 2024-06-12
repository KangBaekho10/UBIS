package org.ubis.ubis.domain.product.model

import jakarta.persistence.*
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

    @Column(name = "imgs")
    var imgs: String,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Product.toProductResponse(): ProductResponse = ProductResponse(
    id = id!!,
    name = name,
    description = description,
    price = price,
    createdAt = createdAt,
    imgs = imgs
)