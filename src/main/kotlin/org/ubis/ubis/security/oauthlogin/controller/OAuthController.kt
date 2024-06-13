package org.ubis.ubis.security.oauthlogin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.ubis.ubis.security.oauthlogin.service.OAuthService

@RestController
class OAuthController(
    private val oAuthService: OAuthService
) {

    @GetMapping("/oauth/kakao")
    fun getKakaoLoginPage(): String {
        return oAuthService.getKakaoLoginPage()
    }

    @GetMapping("/kakao/callback")
    fun kakaoCallback(
        @RequestParam code: String
    ): String {
        return oAuthService.kakaoLogin(code)
    }

    @GetMapping("/oauth/naver")
    fun getNaverLoginPage(): String {
        return oAuthService.getNaverLoginPage()
    }

    @GetMapping("/naver/callback")
    fun naverCallback(
        @RequestParam code: String
    ): String {
        return oAuthService.naverLogin(code)
    }
}