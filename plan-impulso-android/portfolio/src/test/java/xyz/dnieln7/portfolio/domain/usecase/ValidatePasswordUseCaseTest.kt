package xyz.dnieln7.portfolio.domain.usecase

import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var useCase: ValidatePasswordUseCase

    @Before
    fun setup() {
        useCase = ValidatePasswordUseCase()
    }

    @Test
    fun `GIVEN a password with a length of 10 WHEN invoke THEN return true`() {
        val password = "ABCDEFGHIJ"
        val result = useCase(password)

        result shouldBe true
    }

    @Test
    fun `GIVEN a password with a length of 2 WHEN invoke THEN return false`() {
        val password = "AB"
        val result = useCase(password)

        result shouldBe false
    }
}
