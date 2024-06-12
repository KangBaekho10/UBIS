package org.ubis.ubis.domain.product.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
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

    @Column(name = "create_at")
    var createAt: LocalDateTime = LocalDateTime.now(),

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
    createAt = createAt,
    imgs = imgs
)