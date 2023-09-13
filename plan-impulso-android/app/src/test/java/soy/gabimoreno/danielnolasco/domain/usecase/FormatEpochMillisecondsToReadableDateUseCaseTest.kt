package soy.gabimoreno.danielnolasco.domain.usecase

import java.util.*
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test

class FormatEpochMillisecondsToReadableDateUseCaseTest {

    private val locale: Locale = Locale.ROOT
    private lateinit var useCase: FormatEpochMillisecondsToReadableDateUseCase

    @Before
    fun setUp() {
        useCase = FormatEpochMillisecondsToReadableDateUseCase(locale)
    }

    @Test
    fun `GIVEN valid seconds WHEN invoke THEN return a formatted string`() {
        val dateTime = DateTime.now()
        val formatter = DateTimeFormat.mediumDateTime().withLocale(locale)

        val result = useCase(dateTime.millis)
        val expectedResult = formatter.print(dateTime)

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN invalid seconds WHEN invoke THEN return an empty string`() {
        val result = useCase(-123489)

        result.shouldBeEmpty()
    }
}
