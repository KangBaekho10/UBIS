package org.ubis.ubis.security.oauthlogin.controller

import org.ubis.ubis.security.oauthlogin.service.OAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuthController(
    private val oAuthService: OAuthService
) {

    @GetMapping("/oauth/kakao")
    fun getLoginPage() : String{
        return oAuthService.getKakaoLoginPage()
    }

    @GetMapping("/kakao/callback")
    fun callback(
        @RequestParam code :String
    ) : String{
        return oAuthService.kakaoLogin(code)
    }

    @GetMapping("/oauth/naver")
    fun getNaverLoginPage() : String{
        return oAuthService.getNaverLoginPage()
    }

    @GetMapping("/naver/callback")
    fun naverCallback(
        @RequestParam code :String
    ) : String{
        return oAuthService.naverLogin(code)
    }
}