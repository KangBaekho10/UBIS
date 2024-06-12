package org.ubis.ubis.domain.member.dto

import jakarta.validation.constraints.Pattern
import org.ubis.ubis.domain.member.model.Role

data class CreateMemberRequest(
    // 요구사항
    // username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성
    // password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자
    @field:Pattern(
        regexp = "^[a-z0-9]{4,10}+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$",
        message = "@ 앞의 문자열이 최소 4자이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성된 이메일이어야 합니다."
    )
    val email: String,
    val name: String,
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\^\$*.\\[\\]{}\\(\\)\\?\\-\\\"!@#%&/\\\\,><':;|_~`]).{8,15}\$",
        message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 포함해야 합니다."
    )
    val password: String,
    val phoneNumber: String,
    val role: Role
)