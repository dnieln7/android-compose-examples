package xyz.dnieln7.core.domain.provider

import javax.inject.Inject
import javax.inject.Singleton
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

@Singleton
class DateProvider @Inject constructor() {

    fun nowUTC(): DateTime {
        return DateTime.now(DateTimeZone.UTC)
    }

    fun fromMillisUTC(millis: Long): DateTime {
        return DateTime(millis, DateTimeZone.UTC)
    }

    fun fromISO8601(string: String): DateTime {
        return DateTime.parse(string)
    }
}
