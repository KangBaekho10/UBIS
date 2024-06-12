package org.ubis.ubis.security.oauth

import org.ubis.ubis.common.config.type.OAuth2Provider

open class OAuth2LoginUserInfo(
    val provider: OAuth2Provider,
    val id: String,
    val email: String
)