package org.ubis.ubis.security.oauth.kakao

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.ubis.ubis.security.oauth.OAuthUserInfoResponse
import org.ubis.ubis.security.oauth.UserInfoProperties


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoOAuthUserInfo(
    val id: Long,
    val properties: UserInfoProperties
) : OAuthUserInfoResponse
