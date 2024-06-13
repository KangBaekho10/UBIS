package org.ubis.ubis.security.oauthlogin.model

import jakarta.persistence.*

@Entity
@Table(name = "socialmember")
class SocialMember(
    var name: String,
    @Column(name = "oauth_provider")
    var oAuthProvider: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}