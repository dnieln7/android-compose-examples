package soy.gabimoreno.danielnolasco.data.database.dao

import androidx.room.*
import soy.gabimoreno.danielnolasco.data.database.model.WalkingSessionDbModel
import soy.gabimoreno.danielnolasco.data.database.relation.CompleteWalkingSession

@Dao
interface WalkingSessionDbModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalkingSessionDbModel(walkingSessionDbModel: WalkingSessionDbModel)

    @Query("SELECT * FROM tb_walking_sessions")
    suspend fun getWalkingSessionDbModels(): List<WalkingSessionDbModel>

    @Query("SELECT * FROM tb_walking_sessions WHERE startTime < :timestamp")
    suspend fun getWalkingSessionDbModelsWithStartTimeOlderThan(timestamp: Long): List<WalkingSessionDbModel>

    @Transaction
    @Query("SELECT * FROM tb_walking_sessions WHERE startTime = :startTime")
    suspend fun getCompleteWalkingSessionByStartTime(startTime: Long): CompleteWalkingSession?

    @Query("UPDATE tb_walking_sessions SET duration = :duration WHERE startTime = :startTime")
    suspend fun updateWalkingSessionDbModelDuration(startTime: Long, duration: Long)

    @Query("UPDATE tb_walking_sessions SET endTime = :endTime WHERE startTime = :startTime")
    suspend fun updateWalkingSessionDbModelAsFinished(startTime: Long, endTime: Long)

    @Query("DELETE FROM tb_walking_sessions WHERE startTime = :startTime")
    suspend fun deleteWalkingSessionDbModelsByStartTime(startTime: Long)
}
