package org.ubis.ubis.domain.member.dto

import org.ubis.ubis.domain.member.model.Role

data class MemberResponse(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: Role,
)