package soy.gabimoreno.danielnolasco.domain.usecase

import java.util.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class FormatEpochMillisecondsToReadableDateUseCase(private val locale: Locale) {
    operator fun invoke(epochMilliseconds: Long): String {
        return if (epochMilliseconds >= 0) {
            val dateTime = DateTime(epochMilliseconds)

            DateTimeFormat.mediumDateTime().withLocale(locale).print(dateTime)
        } else {
            ""
        }
    }
}
