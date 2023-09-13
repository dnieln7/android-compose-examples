package soy.gabimoreno.danielnolasco.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.res.R

class FormatSecondsToReadableDurationUseCaseTest {

    private lateinit var useCase: FormatSecondsToReadableDurationUseCase

    private val fakeResourceProvider = object : ResourceProvider {
        override fun getString(id: Int): String {
            return ""
        }

        override fun getString(id: Int, vararg args: Any): String {
            return when (id) {
                R.string.duration_hours_placeholder_minutes_placeholder_seconds_placeholder -> {
                    "${args[0]} Hours ${args[1]} Minutes ${args[2]} Seconds"
                }
                R.string.duration_minutes_placeholder_seconds_placeholder -> {
                    "${args[0]} Minutes ${args[1]} Seconds"
                }
                R.string.duration_seconds_placeholder -> {
                    "${args[0]} Seconds"
                }
                else -> ""
            }
        }
    }

    @Before
    fun setUp() {
        useCase = FormatSecondsToReadableDurationUseCase(fakeResourceProvider)
    }

    @Test
    fun `GIVEN 4000 seconds WHEN invoke THEN return hours minutes seconds string`() {
        val result = useCase(4000)
        val expectedResult = "1 Hours 6 Minutes 40 Seconds"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN 100 seconds WHEN invoke THEN return minutes seconds string`() {
        val result = useCase(100)
        val expectedResult = "1 Minutes 40 Seconds"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN 30 seconds WHEN invoke THEN return seconds string`() {
        val result = useCase(30)
        val expectedResult = "30 Seconds"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN 0 seconds WHEN invoke THEN return seconds string`() {
        val result = useCase(0)
        val expectedResult = "0 Seconds"

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN negative seconds WHEN invoke THEN return 0 seconds string`() {
        val result = useCase(-200)
        val expectedResult = "0 Seconds"

        result shouldBeEqualTo expectedResult
    }
}
