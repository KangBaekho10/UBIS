package org.ubis.ubis.security.oauth.naver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class NaverResponse<T>(
    @JsonProperty("resultcode") val code: String,
    val message: String,
    val response: T
)