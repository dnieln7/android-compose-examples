package soy.gabimoreno.danielnolasco.data.datasource.walkingsession

import soy.gabimoreno.danielnolasco.data.database.dao.WalkingSessionDbModelDao
import soy.gabimoreno.danielnolasco.data.mapper.toDbModel
import soy.gabimoreno.danielnolasco.data.mapper.toDomain
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

class LocalWalkingSessionDataSource(private val walkingSessionDbModelDao: WalkingSessionDbModelDao) {

    suspend fun saveWalkingSession(walkingSession: WalkingSession) {
        walkingSessionDbModelDao.insertWalkingSessionDbModel(walkingSession.toDbModel())
    }

    suspend fun getWalkingSessions(): List<WalkingSession> {
        return walkingSessionDbModelDao.getWalkingSessionDbModels().map { it.toDomain() }
    }

    suspend fun getWalkingSessionsWithStartTimeOlderThan(timestamp: Long): List<WalkingSession> {
        return walkingSessionDbModelDao.getWalkingSessionDbModelsWithStartTimeOlderThan(timestamp)
            .map { it.toDomain() }
    }

    suspend fun getWalkingSessionByStartTime(startTime: Long): WalkingSession? {
        return walkingSessionDbModelDao.getCompleteWalkingSessionByStartTime(startTime)
            ?.toDomain()
    }

    suspend fun updateWalkingSessionDuration(startTime: Long, duration: Long) {
        walkingSessionDbModelDao.updateWalkingSessionDbModelDuration(startTime, duration)
    }

    suspend fun updateWalkingSessionAsFinished(startTime: Long, endTime: Long) {
        walkingSessionDbModelDao.updateWalkingSessionDbModelAsFinished(startTime, endTime)
    }

    suspend fun deleteWalkingSessionsByStartTime(startTime: Long) {
        walkingSessionDbModelDao.deleteWalkingSessionDbModelsByStartTime(startTime)
    }
}
