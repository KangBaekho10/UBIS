package org.ubis.ubis.domain.member.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.ubis.ubis.domain.exception.ModelNotFoundException
import org.ubis.ubis.domain.exception.ReusedPasswordException
import org.ubis.ubis.domain.member.dto.MemberResponse
import org.ubis.ubis.domain.member.dto.UpdateMemberRequest
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

    @Transactional
    fun updateMember(
        memberId: Long,
        updateMemberRequest: UpdateMemberRequest
    ): MemberResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        if (updateMemberRequest.name != null) {
            member.name = updateMemberRequest.name
        }

        if (updateMemberRequest.phoneNumber != null) {
            member.phoneNumber = updateMemberRequest.phoneNumber
        }

        if (updateMemberRequest.password != null) {
            // 이전에 사용했던 비밀번호 이력을 가져옴
            val pwHistory = member.pwHistory.split(",").toMutableList() // [hh23,  dddd,  1234]

            // 수정 요청한 비밀번호가 이전에 사용했던 적이 있는지 확인
            if (pwHistory.contains(updateMemberRequest.password)) {
                throw ReusedPasswordException("이전에 사용했던 비밀번호는 사용할 수 없습니다.")
            }

            // 일치하는 비밀번호가 없으면 기존 변경 이력횟수를 확인
            if (pwHistory.size >= 3) { // 3번 이상이면
                // 첫 번째 요소(가장 오래 전에 사용했던 비밀번호)를 제거
                pwHistory.removeFirst()
            }

            pwHistory.add(updateMemberRequest.password) // TODO: passwordEncode로 변경 후 add 해야 함

            // 변경된 비밀번호 List를 비밀번호 이력에 추가
            member.pwHistory = pwHistory.joinToString(",")
            member.password = pwHistory.last()
        }

        return member.toResponse()
    }
}