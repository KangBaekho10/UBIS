package org.ubis.ubis.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val authorities: Collection<GrantedAuthority>,
    val joinType: String
) {
    constructor(id: Long, role: Set<String>, joinType: String) : this(
        id,
        role.map { SimpleGrantedAuthority("ROLE_$it") },
        joinType
    )
}