package org.ubis.ubis.domain.member.dto

data class UpdateMemberRequest(
    val name: String?,
    val phoneNumber: String?,
    val password: String?
)