package org.ubis.ubis.security.oauthlogin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ubis.ubis.common.config.type.OAuth2Provider
import org.ubis.ubis.security.oauthlogin.model.SocialMember

interface SocialMemberRepository : JpaRepository<SocialMember, Long> {

    fun findByProviderAndProviderId(provider: OAuth2Provider, providerId: String): SocialMember?
}