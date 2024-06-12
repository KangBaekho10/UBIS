package org.ubis.ubis.domain.member.model

import jakarta.persistence.*

class Member(
    val email: String,
    var name: String,
    var password: String,

    @Column(name = "phone_number")
    var phoneNumber: String,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.CUSTOMER
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}