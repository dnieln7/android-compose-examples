package soy.gabimoreno.danielnolasco.domain.usecase

import arrow.core.Either
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository

class GetWalkingSessionsOlderThan7DaysUseCase(
    private val walkingSessionRepository: WalkingSessionRepository,
) {
    suspend operator fun invoke(): Either<Throwable, List<WalkingSession>> {
        val nowMinus7Days = DateTime.now(DateTimeZone.UTC).minusDays(SEVEN_DAYS).millis

        return walkingSessionRepository.getWalkingSessionsWithStartTimeOlderThan(nowMinus7Days)
    }
}

private const val SEVEN_DAYS = 7
