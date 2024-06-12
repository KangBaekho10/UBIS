package org.ubis.ubis.domain.member.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.ubis.ubis.domain.member.dto.CreateMemberRequest
import org.ubis.ubis.domain.member.dto.MemberResponse
import org.ubis.ubis.domain.member.dto.UpdateMemberRequest
import org.ubis.ubis.domain.member.service.MemberService

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping
    fun createMember(@Valid @RequestBody createMemberRequest: CreateMemberRequest): ResponseEntity<String> {
        memberService.createMember(createMemberRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.")
    }

    // TODO: security 구현 완료 후 파라미터 변경
    @GetMapping
    fun getMember(@RequestParam memberId: Long): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(memberId))
    }

    // TODO: security 구현 완료 후 파라미터 변경
    @PostMapping("/password-check")
    fun passwordCheck(
        @RequestParam memberId: Long,
        @RequestBody password: String
    ): ResponseEntity<Unit> {
        memberService.passwordCheck(memberId, password)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PutMapping
    fun updateMember(
        @RequestParam memberId: Long,
        @Valid @RequestBody updateMemberRequest: UpdateMemberRequest
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(memberId, updateMemberRequest))
    }
}