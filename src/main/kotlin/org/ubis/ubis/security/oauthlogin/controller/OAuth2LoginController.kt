package org.ubis.ubis.security.oauthlogin.controller

import org.ubis.ubis.security.oauthlogin.service.OAuth2LoginService
import org.ubis.ubis.security.oauth.OAuth2ClientService
import org.ubis.ubis.common.config.type.OAuth2Provider
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuth2LoginController(
    private val oAuth2LoginService: OAuth2LoginService,
    private val oAuth2ClientService: OAuth2ClientService
) {

    @GetMapping("/oauth/{provider}/")
    fun redirectLoginPage(@PathVariable provider: OAuth2Provider, response: HttpServletResponse) {
        oAuth2ClientService.generateLoginPageUrl(provider)
            .let { response.sendRedirect(it) }
    }

    @GetMapping("/{provider}/callback/")
    fun callback(
        @PathVariable provider: OAuth2Provider,
        @RequestParam(name = "code") authorizationCode: String
    ): String {
        return oAuth2LoginService.login(provider, authorizationCode)
    }
}