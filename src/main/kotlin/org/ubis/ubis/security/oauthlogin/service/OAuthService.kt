package org.ubis.ubis.security.oauthlogin.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.ubis.ubis.domain.member.model.Role
import org.ubis.ubis.security.jwt.JwtPlugin
import org.ubis.ubis.security.oauth.kakao.KakaoOAuthClient
import org.ubis.ubis.security.oauth.naver.NaverOAuthClient
import org.ubis.ubis.domain.member.repository.MemberRepository
import org.ubis.ubis.domain.member.model.Member

@Service
class OAuthService(
    private val kakaoOAuthClient: KakaoOAuthClient,
    private val naverOAuthClient: NaverOAuthClient,
    private val memberRepository: MemberRepository,
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

        val userInfo = WebClient.create("https://openapi.naver.com/v1/nid/me")
            .get()
            .header("Authorization", "Bearer $accessToken")
            .retrieve() // 응답값을 받게 해주는 메서드
            .bodyToMono(Map::class.java) // ResponseBody Type
            .block() // 동기 방식으로 변경

        val naverAccount = userInfo?.get("response") as Map<*, *>
        val nickname = naverAccount["name"] as String

        val members = (memberRepository.findByName(nickname)
            ?: memberRepository.save(
                Member(
                    name = nickname,
                    oAuthProvider = "naver",
                    email = " ",
                    password = " ",
                    phoneNumber = " ",
                    pwHistory = " ",
                    role = Role.CUSTOMER
                )
            ))

        return jwtPlugin.generateAccessToken(members.id.toString(),"CUSTOMER","SOCIAL")
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

        val members = (memberRepository.findByName(nickname)
            ?: memberRepository.save(
                Member(
                    name = nickname,
                    oAuthProvider = "kakao",
                    email = " ",
                    password = " ",
                    phoneNumber = " ",
                    pwHistory = " ",
                    role = Role.CUSTOMER
                )
            ))

        return jwtPlugin.generateAccessToken(members.id.toString(), "CUSTOMER", "SOCIAL")
    }
}