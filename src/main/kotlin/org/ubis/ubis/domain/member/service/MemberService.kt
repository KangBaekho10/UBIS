package org.ubis.ubis.domain.member.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.exception.ModelNotFoundException
import org.ubis.ubis.domain.member.dto.MemberResponse
import org.ubis.ubis.domain.member.model.toResponse
import org.ubis.ubis.domain.member.repository.MemberRepository

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun getMember(memberId: Long): MemberResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return member.toResponse()
    }
}