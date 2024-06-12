package org.ubis.ubis.domain.member.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


class MemberTests : BehaviorSpec({

    Given("Member Information") {
        val email = "test@test.com"
        val name = "test user"
        val password = "1234"
        val phoneNumber = "010-1234-5678"

        When("Member Constructor") {
            val result = Member(
                email = email,
                name = name,
                password = password,
                phoneNumber = phoneNumber
            )

            Then("Result should be") {
                result.email shouldBe email
                result.name shouldBe name
                result.password shouldBe password
                result.phoneNumber shouldBe phoneNumber
                result.role shouldBe Role.CUSTOMER
            }
        }
    }
})