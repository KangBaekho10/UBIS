package org.ubis.ubis.security.oauthlogin.repository

import org.ubis.ubis.security.oauthlogin.model.SocialMember
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.ubis.ubis.common.config.type.OAuth2Provider

@Repository
interface SocialMemberRepository : CrudRepository<SocialMember, Long> {

    fun findByProviderAndProviderId(kakao: OAuth2Provider, id: String): SocialMember?
}