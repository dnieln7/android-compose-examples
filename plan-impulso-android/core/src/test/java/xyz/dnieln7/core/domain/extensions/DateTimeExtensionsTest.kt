package xyz.dnieln7.core.domain.extensions

import org.amshove.kluent.shouldBeEqualTo
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Test

class DateTimeExtensionsTest {

    @Test
    fun `GIVEN the happy path WHEN stringifyToPortfolioFormat THEN return the expected string`() {
        val dateTime = DateTime(
            /* year = */ 2021,
            /* monthOfYear = */ 10,
            /* dayOfMonth = */ 9,
            /* hourOfDay = */ 19,
            /* minuteOfHour = */ 22,
            /* secondOfMinute = */ 51,
            /* millisOfSecond = */ 770,
            /* zone = */ DateTimeZone.UTC,
        )

        val result = dateTime.stringifyToPortfolioFormat()
        val expectedResult = "2021-10-09T19:22:51.770Z"

        result shouldBeEqualTo expectedResult
    }
}
