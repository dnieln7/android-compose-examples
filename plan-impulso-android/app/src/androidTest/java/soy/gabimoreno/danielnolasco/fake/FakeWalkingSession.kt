package soy.gabimoreno.danielnolasco.fake

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

fun buildWalkingSession(daysOld: Int = 0): WalkingSession {
    val date = DateTime.now(DateTimeZone.UTC).minusDays(daysOld)

    return WalkingSession(
        startTime = date.millis,
        endTime = date.plusHours(1).millis,
        duration = 3600,
        locationEvents = buildLocationEvents(),
    )
}
