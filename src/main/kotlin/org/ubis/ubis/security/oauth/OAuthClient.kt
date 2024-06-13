package org.ubis.ubis.security.oauth

interface OAuthClient {
    fun getLoginPageUrl() : String
    fun getAccessToken(code : String) : String
    fun getUserInfo(accessToken : String) : OAuthUserInfoResponse
}