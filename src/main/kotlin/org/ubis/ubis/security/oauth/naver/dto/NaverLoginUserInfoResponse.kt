package org.ubis.ubis.security.oauth.naver.dto

import org.ubis.ubis.security.oauth.OAuth2LoginUserInfo
import org.ubis.ubis.common.config.type.OAuth2Provider
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class NaverLoginUserInfoResponse(
    id: String,
    nickname: String
) : OAuth2LoginUserInfo(
    provider = OAuth2Provider.NAVER,
    id = id,
    nickname = nickname
)