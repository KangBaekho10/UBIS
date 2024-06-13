package org.ubis.ubis.domain.member.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.ubis.ubis.domain.exception.AlreadyExistsException
import org.ubis.ubis.domain.exception.ModelNotFoundException
import org.ubis.ubis.domain.exception.ReusedPasswordException
import org.ubis.ubis.domain.member.dto.*
import org.ubis.ubis.domain.member.model.Member
import org.ubis.ubis.domain.member.model.Role
import org.ubis.ubis.domain.member.model.toResponse
import org.ubis.ubis.domain.member.repository.MemberRepository
import org.ubis.ubis.security.UserPrincipal
import org.ubis.ubis.security.jwt.JwtPlugin

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    fun getMember(): MemberResponse {
        val memberId = getMemberIdFromToken()
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)
        return member.toResponse()
    }

    @Transactional
    fun updateMember(
        updateMemberRequest: UpdateMemberRequest
    ): MemberResponse {
        val memberId = getMemberIdFromToken()
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        if (updateMemberRequest.name != null) {
            member.name = updateMemberRequest.name
        }

        if (updateMemberRequest.phoneNumber != null) {
            val isExistPhoneNumber = memberRepository.existsByPhoneNumber(updateMemberRequest.phoneNumber)

            if (isExistPhoneNumber) { // DB에 전화번호가 있다면
                throw AlreadyExistsException(updateMemberRequest.phoneNumber, "전화번호")
            }

            member.phoneNumber = updateMemberRequest.phoneNumber
        }

        if (updateMemberRequest.password != null) {
            // 이전에 사용했던 비밀번호 이력을 가져옴
            val pwHistory = member.pwHistory.split(",").toMutableList() // [hh23,  dddd,  1234]

            // 수정 요청한 비밀번호가 이전에 사용했던 적이 있는지 확인
            val isExistPassword = pwHistory.filter { passwordEncoder.matches(updateMemberRequest.password, it) }.size

            if (isExistPassword > 0) {
                throw ReusedPasswordException("이전에 사용했던 비밀번호는 사용할 수 없습니다.")
            }

            // 일치하는 비밀번호가 없으면 기존 변경 이력횟수를 확인
            if (pwHistory.size >= 3) { // 3번 이상이면
                // 첫 번째 요소(가장 오래 전에 사용했던 비밀번호)를 제거
                pwHistory.removeFirst()
            }

            pwHistory.add(passwordEncoder.encode(updateMemberRequest.password))

            // 변경된 비밀번호 List를 비밀번호 이력에 추가
            member.pwHistory = pwHistory.joinToString(",")
            member.password = pwHistory.last()
        }

        return member.toResponse()
    }

    fun signup(request: CreateMemberRequest): MemberResponse {
        if (memberRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return memberRepository.save(
            Member(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                phoneNumber = request.phoneNumber,
                pwHistory = null.toString(),
                role = when (request.role) {
                    "BUSINESS" -> Role.BUSINESS
                    "CUSTOMER" -> Role.CUSTOMER
                    else -> throw IllegalArgumentException("Invalid role")
                }

            )
        ).toResponse()
    }

    fun login(request: MemberRequest): LoginResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw ModelNotFoundException("Member", null)


        if (!passwordEncoder.matches(request.password, member.password)) {
            throw IllegalStateException("Passwords do not match")
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                name = member.name
            )
        )
    }

    @Transactional
    fun createMember(createMemberRequest: CreateMemberRequest): MemberResponse {

        val isExistEmail = memberRepository.existsByEmail(createMemberRequest.email)

        if (isExistEmail) { // 이미 존재하는 이메일일 경우 예외 발생
            throw AlreadyExistsException(createMemberRequest.email, "이메일")
        }

        val isExistPhoneNumber = memberRepository.existsByPhoneNumber(createMemberRequest.phoneNumber)

        if (isExistPhoneNumber) { // 이미 존재하는 전화번호일 경우 예외 발생
            throw AlreadyExistsException(createMemberRequest.phoneNumber, "전화번호")
        }

        val password = passwordEncoder.encode(createMemberRequest.password)

        return memberRepository.save(
            Member(
                name = createMemberRequest.name,
                email = createMemberRequest.email,
                password = password,
                phoneNumber = createMemberRequest.phoneNumber,
                pwHistory = password
            )
        ).toResponse()

    }

    fun passwordCheck(password: String) {
        val memberId = getMemberIdFromToken()
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        if (!passwordEncoder.matches(password, member.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }
    }

    @Transactional
    fun deleteMember() {
        val memberId = getMemberIdFromToken()
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        memberRepository.delete(member)
    }

    fun getMemberIdFromToken(): Long? {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return principal.id
    }

    fun matchMemberId(memberId: Long): Boolean { // Token의 ID와 파라미터ID를 비교
        return getMemberIdFromToken() == memberId
    }
}