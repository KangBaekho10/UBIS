package org.ubis.ubis.domain.member.dto

data class MemberResponse(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String
)