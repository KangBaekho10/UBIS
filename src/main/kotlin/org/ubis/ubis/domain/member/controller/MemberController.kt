package org.ubis.ubis.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.ubis.ubis.domain.member.dto.MemberResponse
import org.ubis.ubis.domain.member.dto.UpdateMemberRequest
import org.ubis.ubis.domain.member.service.MemberService

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    // TODO: security 구현 완료 후 파라미터 변경
    @GetMapping
    fun getMember(@RequestParam memberId: Long): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(memberId))
    }

    // TODO: 비밀번호를 확인하는 함수 필요

    @PutMapping
    fun updateMember(
        @RequestParam memberId: Long,
        @RequestBody updateMemberRequest: UpdateMemberRequest) : ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(memberId, updateMemberRequest))
    }
}