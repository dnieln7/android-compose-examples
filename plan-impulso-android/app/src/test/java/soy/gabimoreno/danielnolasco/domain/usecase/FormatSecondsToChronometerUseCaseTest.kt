package soy.gabimoreno.danielnolasco.domain.usecase

import java.util.*
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class FormatSecondsToChronometerUseCaseTest {

    private lateinit var useCase: FormatSecondsToChronometerUseCase

    @Before
    fun setUp() {
        useCase = FormatSecondsToChronometerUseCase(Locale.ROOT)
    }

    @Test
    fun `GIVEN 3600 seconds WHEN invoke THEN return 01_00_00`() {
        val result = useCase(3600)
        val expectedResult = "01:00:00"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN 60 seconds WHEN invoke THEN return 00_01_00`() {
        val result = useCase(60)
        val expectedResult = "00:01:00"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN 1 seconds WHEN invoke THEN return 00_00_01`() {
        val result = useCase(1)
        val expectedResult = "00:00:01"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN 0 seconds WHEN invoke THEN return 00_00_00`() {
        val result = useCase(0)
        val expectedResult = "00:00:00"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN negative seconds WHEN invoke THEN return 00_00_00`() {
        val result = useCase(-1)
        val expectedResult = "00:00:00"

        result shouldBeEqualTo expectedResult
    }
}
