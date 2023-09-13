package xyz.dnieln7.portfolio.domain.usecase

import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var useCase: ValidateEmailUseCase

    @Before
    fun setup() {
        useCase = ValidateEmailUseCase()
    }

    @Test
    fun `GIVEN a valid email WHEN invoke THEN return true`() {
        val email = "dnieln7@gmail.com"
        val result = useCase(email)

        result shouldBe true
    }

    @Test
    fun `GIVEN an email without @ WHEN invoke THEN return false`() {
        val email = "dnieln7gmail.com"
        val result = useCase(email)

        result shouldBe false
    }

    @Test
    fun `GIVEN an email without dot WHEN invoke THEN return false`() {
        val email = "dnieln7@gmailcom"

        val result = useCase(email)

        result shouldBe false
    }

    @Test
    fun `GIVEN a random string WHEN invoke THEN return false`() {
        val email = "sa87d1binu"
        val result = useCase(email)

        result shouldBe false
    }

    @Test
    fun `GIVEN an empty string WHEN invoke THEN return false`() {
        val email = ""
        val result = useCase(email)

        result shouldBe false
    }
}
