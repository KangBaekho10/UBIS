package org.ubis.ubis.security.oauthlogin.converter

import org.springframework.core.convert.converter.Converter
import org.ubis.ubis.common.config.type.OAuth2Provider

class OAuth2ProviderConverter : Converter<String, OAuth2Provider> {

    override fun convert(source: String): OAuth2Provider {
        return runCatching {
            OAuth2Provider.valueOf(source.uppercase())
        }.getOrElse {
            throw IllegalArgumentException()
        }
    }
}