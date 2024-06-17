package org.ubis.ubis.domain.member.dto

import jakarta.validation.constraints.Pattern

data class UpdateMemberRequest(
    val name: String?,
    val phoneNumber: String?,
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\^\$*.\\[\\]{}\\(\\)\\?\\-\\\"!@#%&/\\\\,><':;|_~`]).{8,15}\$",
        message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 포함해야 합니다."
    )
    val password: String?
)