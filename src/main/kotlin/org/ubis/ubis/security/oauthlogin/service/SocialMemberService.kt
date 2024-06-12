package org.ubis.ubis.security.oauthlogin.service

import org.ubis.ubis.security.oauth.OAuth2LoginUserInfo
import org.ubis.ubis.domain.member.model.SocialMember
import org.ubis.ubis.domain.member.repository.SocialMemberRepository
import org.springframework.stereotype.Service

@Service
class SocialMemberService(
    private val socialMemberRepository: SocialMemberRepository
) {

    fun registerIfAbsent(userInfo: OAuth2LoginUserInfo): SocialMember {
        return socialMemberRepository.findByProviderAndProviderId(userInfo.provider, userInfo.id) ?: run {
            val socialMember = SocialMember(
                provider = userInfo.provider,
                providerId = userInfo.id,
                nickname = userInfo.nickname
            )
            socialMemberRepository.save(socialMember)
        }
    }
}