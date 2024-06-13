package org.ubis.ubis.security.oauthlogin.model

import jakarta.persistence.*
import org.ubis.ubis.common.config.type.OAuth2Provider

@Entity
class SocialMember(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_member")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,
    val providerId: String,
    val nickname: String
)