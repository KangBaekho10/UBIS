package org.ubis.ubis.domain.member.repository

import org.ubis.ubis.common.config.type.OAuth2Provider
import org.ubis.ubis.domain.member.model.SocialMember
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
// 삭제 후 memberRepository에 편입 예정
@Repository
interface SocialMemberRepository : CrudRepository<SocialMember, Long> {

    fun findByProviderAndProviderId(kakao: OAuth2Provider, id: String): SocialMember?
}