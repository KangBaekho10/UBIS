package org.ubis.ubis.security.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.ubis.ubis.security.UserPrincipal
import org.ubis.ubis.security.jwt.JwtAuthenticationToken
import org.ubis.ubis.security.jwt.JwtPlugin

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin,
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val id = it.payload.subject.toLong()
                    val role = it.payload.get("role", String::class.java)
                    val joinType = it.payload.get("joinType", String::class.java)

                    val principal = UserPrincipal(
                        id = id,
                        role = setOf(role),
                        joinType = joinType
                    )
                    val authentication = JwtAuthenticationToken(
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )

                    SecurityContextHolder.getContext().authentication = authentication
                }
        } else { // 토큰이 없으면

            if (request.method != "GET") {
                if (!(request.method == "POST" && request.requestURI.startsWith("/members"))) {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE

                    jacksonObjectMapper().writeValue(response.writer, "No token")
                }
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }
}