package org.ubis.ubis.security.oauthlogin.model

import org.ubis.ubis.common.config.type.OAuth2Provider
import jakarta.persistence.*
// 삭제 후 member에 편입 예정
@Entity
@Table(name = "socialmember")
class SocialMember(

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    val provider: OAuth2Provider,
    @Column(name = "provider_id")
    val providerId: String,
    @Column(name = "nickname")
    val nickname: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}