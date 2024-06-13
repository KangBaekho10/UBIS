package org.ubis.ubis.security.oauth.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.ubis.ubis.security.oauth.OAuth2LoginUserInfo
import org.ubis.ubis.common.config.type.OAuth2Provider
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class KakaoLoginUserInfoResponse(
    id: Long,
    properties: KakaoUserPropertiesResponse
) : OAuth2LoginUserInfo(
    provider = OAuth2Provider.KAKAO,
    id = id.toString(),
    nickname = properties.nickname
)