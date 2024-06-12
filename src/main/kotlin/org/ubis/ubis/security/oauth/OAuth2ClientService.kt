package org.ubis.ubis.security.oauth

import jakarta.transaction.NotSupportedException
import org.springframework.stereotype.Service
import org.ubis.ubis.common.config.type.OAuth2Provider

@Service
class OAuth2ClientService(
    private val clients: List<OAuth2Client>
) {

    fun generateLoginPageUrl(provider: OAuth2Provider): String {
        val client = this.selectClient(provider)
        return client.generateLoginPageUrl()
    }

    fun login(provider: OAuth2Provider, authorizationCode: String): OAuth2LoginUserInfo {
        val client = this.selectClient(provider)
        return client.getAccessToken(authorizationCode)
            .let { client.retrieveUserInfo(it) }
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return clients.find { it.supports(provider) }
            ?: throw NotSupportedException("지원하지 않는 OAuth Provider 입니다.")
    }
}