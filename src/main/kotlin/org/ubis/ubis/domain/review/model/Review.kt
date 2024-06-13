package org.ubis.ubis.domain.review.model

import jakarta.persistence.*
import org.ubis.ubis.domain.product.model.Product
import org.ubis.ubis.domain.review.dto.ReviewResponse
import java.time.LocalDateTime

@Entity
@Table(name = "review")
class Review(
    @Column(name = "content")
    var content: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "member_id")
    val memberId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
