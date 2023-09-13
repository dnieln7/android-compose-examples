package soy.gabimoreno.danielnolasco.domain.repository.walkingsession

import arrow.core.Either
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

interface WalkingSessionRepository {
    suspend fun saveWalkingSession(walkingSession: WalkingSession)
    suspend fun getWalkingSessions(): Either<Throwable, List<WalkingSession>>
    suspend fun getWalkingSessionsWithStartTimeOlderThan(timestamp: Long): Either<Throwable, List<WalkingSession>>
    suspend fun getWalkingSessionByStartTime(startTime: Long): Either<Throwable, WalkingSession?>
    suspend fun updateWalkingSessionDuration(startTime: Long, duration: Long)
    suspend fun updateWalkingSessionAsFinished(startTime: Long, endTime: Long)
    suspend fun deleteWalkingSessionsByStartTime(startTime: Long)
}
