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

//    @PostMapping
//    fun createMember(@Valid @RequestBody createMemberRequest: CreateMemberRequest): ResponseEntity<String> {
//        memberService.createMember(createMemberRequest)
//        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.")
//    }

    @GetMapping
    fun getMember(): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember())
    }

    @PostMapping("/password-check")
    fun passwordCheck(
        @RequestBody password: String
    ): ResponseEntity<Unit> {
        memberService.passwordCheck(password)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PutMapping
    fun updateMember(
        @Valid @RequestBody updateMemberRequest: UpdateMemberRequest
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(updateMemberRequest))
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

    @DeleteMapping
    fun deleteMember(): ResponseEntity<Unit> {
        memberService.deleteMember()
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}