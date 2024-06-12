package org.ubis.ubis.security.oauthlogin.service

import org.springframework.stereotype.Service
import org.ubis.ubis.common.config.JwtHelper
import org.ubis.ubis.common.config.type.OAuth2Provider
import org.ubis.ubis.security.oauth.OAuth2ClientService

@Service
class OAuth2LoginService(
    private val oAuth2ClientService: OAuth2ClientService,
    private val socialMemberService: SocialMemberService,
    private val jwtHelper: JwtHelper
) {

    fun login(provider: OAuth2Provider, authorizationCode: String): String {
        return oAuth2ClientService.login(provider, authorizationCode)
            .let { socialMemberService.registerIfAbsent(it) }
            .let { jwtHelper.generateAccessToken(it.id!!) }
    }
}