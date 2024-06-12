package org.ubis.ubis.domain.member.model

import jakarta.persistence.*
import org.ubis.ubis.domain.member.dto.MemberResponse

@Entity
@Table(name = "member")
class Member(
    val email: String,
    var name: String,
    var password: String,

    @Column(name = "phone_number")
    var phoneNumber: String,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.CUSTOMER,

    @Column(name = "pw_history")
    val pwHistory: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = id!!,
        email = email,
        name = name,
        phoneNumber = phoneNumber
    )
}