package org.ubis.ubis.security.oauthlogin.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.ubis.ubis.security.jwt.JwtPlugin
import org.ubis.ubis.security.oauth.kakao.KakaoOAuthClient
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


        val socialMembers = (socialRepository.findByName(userInfo.response.name!!)
            ?: socialRepository.save(
                SocialMember(
                    name = userInfo.response.name,
                    oAuthProvider = "naver",
                )
            ))

        return jwtPlugin.generateAccessToken("name", socialMembers.name)
    }

    fun kakaoLogin(code: String): String {
        val accessToken = kakaoOAuthClient.getAccessToken(code)

        val userInfo = WebClient.create("https://kapi.kakao.com/v2/user/me")
            .get()
            .header("Authorization", "Bearer $accessToken")
            .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
            .retrieve() // 응답값을 받게 해주는 메서드
            .bodyToMono(Map::class.java) // ResponseBody Type
            .block() // 동기 방식으로 변경

        val kakaoAccount = userInfo?.get("kakao_account") as Map<*, *>
        val profile = kakaoAccount["profile"] as Map<*, *>
        val nickname = profile["nickname"].toString()

        val socialMember = (socialRepository.findByName(nickname)
            ?: socialRepository.save(
                SocialMember(
                    name = nickname,
                    oAuthProvider = "kakao",
                )
            ))

        return jwtPlugin.generateAccessToken("userName", socialMember.name)
    }
}