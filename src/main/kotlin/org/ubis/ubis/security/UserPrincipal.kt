package org.ubis.ubis.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: Long, role: Set<String>) : this(
        id,
        role.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}