package org.ubis.ubis.domain.member.controller

import jakarta.validation.Valid
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

    @GetMapping
    fun getMember(): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember())
    }

    @PostMapping("/password-check")
    fun passwordCheck(
        @RequestBody request: MemberPasswordRequest
    ): ResponseEntity<Unit> {
        memberService.passwordCheck(request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PutMapping
    fun updateMember(
        @Valid @RequestBody updateMemberRequest: UpdateMemberRequest
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(updateMemberRequest))
    }

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: CreateMemberRequest): ResponseEntity<MemberResponse> {
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

    @DeleteMapping
    fun deleteMember(): ResponseEntity<Unit> {
        memberService.deleteMember()
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}