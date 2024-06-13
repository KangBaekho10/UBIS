//package org.ubis.ubis.domain.member.repository
//
//import org.springframework.data.jpa.repository.JpaRepository
//import org.ubis.ubis.common.config.type.OAuth2Provider
//import org.ubis.ubis.domain.member.model.SocialMember
//
//
////@Repository
//interface SocialMemberRepository : JpaRepository<SocialMember, Long> {
//
//    fun findByProviderAndProviderId(provider: OAuth2Provider, providerId: String): SocialMember?
//    //fun findByProviderAndProviderId(kakao: OAuth2Provider, id: String): SocialMember?
//}