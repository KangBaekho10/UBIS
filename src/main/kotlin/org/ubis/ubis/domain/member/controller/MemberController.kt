package org.ubis.ubis.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.ubis.ubis.domain.member.dto.*
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

    @PostMapping("/signup")
    fun signup(@RequestBody request: CreateMemberRequest): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.signup(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: MemberRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.login(request))
    }
}