package org.ubis.ubis.domain.member.model

import org.ubis.ubis.common.config.type.OAuth2Provider
import jakarta.persistence.*
// 삭제 후 member에 편입 예정
@Entity
class SocialMember(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_member_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,
    val providerId: String,
    val nickname: String
)