package org.ubis.ubis.security.oauth.naver

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.ubis.ubis.security.oauth.OAuthUserInfoResponse
import org.ubis.ubis.security.oauth.UserInfoProperties

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class NaverOAuthUserInfo(
    val resultcode: String,
    val message: String,
    val response: UserInfoProperties
) : OAuthUserInfoResponse
