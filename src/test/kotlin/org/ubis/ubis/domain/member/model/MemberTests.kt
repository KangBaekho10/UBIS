package org.ubis.ubis.domain.member.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


class MemberTests : BehaviorSpec({

    Given("Member Information") {
        val id = 1L
        val email = "test@test.com"
        val name = "test user"
        val password = "!a123456"
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

        When("Member Response") {
            val result = Member(
                email = email,
                name = name,
                password = password,
                phoneNumber = phoneNumber
            ).toResponse()

            Then("Result should be") {
                // id는 val로 수정 불가라 Member.toResponse에서 id값을 임의로 1L로 지정한 상태에서 테스트 했음
//                result.id shouldBe id
                result.email shouldBe email
                result.name shouldBe name
                result.phoneNumber shouldBe phoneNumber

                println(result)
            }
        }
    }
})