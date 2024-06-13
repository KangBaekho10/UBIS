package org.ubis.ubis.security.oauthlogin.service

import org.springframework.stereotype.Service
import org.ubis.ubis.security.jwt.JwtPlugin
import org.ubis.ubis.security.oauth.kakao.KakaoOAuthClient
import org.ubis.ubis.security.oauth.kakao.KakaoOAuthUserInfo
import org.ubis.ubis.security.oauth.naver.NaverOAuthClient
import org.ubis.ubis.security.oauth.naver.NaverOAuthUserInfo
import org.ubis.ubis.security.oauthlogin.model.SocialMember
import org.ubis.ubis.security.oauthlogin.repository.SocialRepository

@Service
class OAuthService(
    private val kakaoOAuthClient: KakaoOAuthClient,
    private val naverOAuthClient: NaverOAuthClient,
    private val socialRepository: SocialRepository,
    private val jwtPlugin: JwtPlugin
) {
    fun getNaverLoginPage(): String {
        return naverOAuthClient.getLoginPageUrl()
    }

    fun getKakaoLoginPage(): String {
        return kakaoOAuthClient.getLoginPageUrl()
    }

    fun naverLogin(code: String): String {
        val accessToken = naverOAuthClient.getAccessToken(code)
        val userInfo = naverOAuthClient.getUserInfo(accessToken) as NaverOAuthUserInfo
        val socialMember = (socialRepository.findByName(userInfo.response.name)
            ?: socialRepository.save(
                SocialMember(
                    name = userInfo.response.name,
                    oAuthProvider = "naver",
                )
            ))

        return jwtPlugin.generateAccessToken("name", socialMember.name)
    }

    fun kakaoLogin(code: String): String {
        val accessToken = kakaoOAuthClient.getAccessToken(code)
        val userInfo = kakaoOAuthClient.getUserInfo(accessToken) as KakaoOAuthUserInfo

        val socialMember = (socialRepository.findByName(userInfo.properties.name)
            ?: socialRepository.save(
                SocialMember(
                    name = userInfo.properties.name,
                    oAuthProvider = "kakao",
                )
            ))

        return jwtPlugin.generateAccessToken("userName", socialMember.name)
    }
}