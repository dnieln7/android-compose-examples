package soy.gabimoreno.danielnolasco.data.repository.walkingsession

import arrow.core.Either
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository

class DefaultWalkingSessionRepository(
    private val localWalkingSessionDataSource: LocalWalkingSessionDataSource,
) : WalkingSessionRepository {

    override suspend fun saveWalkingSession(walkingSession: WalkingSession) {
        localWalkingSessionDataSource.saveWalkingSession(walkingSession)
    }

    override suspend fun getWalkingSessions(): Either<Throwable, List<WalkingSession>> {
        return Either.catch { localWalkingSessionDataSource.getWalkingSessions() }
    }

    override suspend fun getWalkingSessionsWithStartTimeOlderThan(
        timestamp: Long,
    ): Either<Throwable, List<WalkingSession>> {
        return Either.catch {
            localWalkingSessionDataSource.getWalkingSessionsWithStartTimeOlderThan(timestamp)
        }
    }

    override suspend fun getWalkingSessionByStartTime(startTime: Long): Either<Throwable, WalkingSession?> {
        return Either.catch { localWalkingSessionDataSource.getWalkingSessionByStartTime(startTime) }
    }

    override suspend fun updateWalkingSessionDuration(startTime: Long, duration: Long) {
        localWalkingSessionDataSource.updateWalkingSessionDuration(startTime, duration)
    }

    override suspend fun updateWalkingSessionAsFinished(startTime: Long, endTime: Long) {
        localWalkingSessionDataSource.updateWalkingSessionAsFinished(startTime, endTime)
    }

    override suspend fun deleteWalkingSessionsByStartTime(startTime: Long) {
        localWalkingSessionDataSource.deleteWalkingSessionsByStartTime(startTime)
    }
}
