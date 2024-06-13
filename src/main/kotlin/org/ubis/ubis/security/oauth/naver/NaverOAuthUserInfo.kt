package org.ubis.ubis.security.oauth.naver

import org.ubis.ubis.security.oauth.OAuthUserInfoResponse

data class NaverOAuthUserInfo (
    val resultcode : String,
    val message : String,
    val response : NaverUserInfoProperties
) : OAuthUserInfoResponse
