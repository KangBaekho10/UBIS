package org.ubis.ubis.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ubis.ubis.domain.member.model.Member

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}