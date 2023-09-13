package soy.gabimoreno.danielnolasco.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import soy.gabimoreno.danielnolasco.data.database.model.LocationEventDbModel

@Dao
interface LocationEventDbModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationEventDbModel(locationEventDbModel: LocationEventDbModel)

    @Query("SELECT * FROM tb_location_events WHERE ownerStartTime = :ownerStartTime")
    suspend fun getLocationEventDbModelsByOwnerStartTime(ownerStartTime: Long): List<LocationEventDbModel>

    @Query(
        "SELECT * FROM tb_location_events WHERE ownerStartTime = :ownerStartTime ORDER BY ownerStartTime ASC"
    )
    fun observeLocationEventDbModelsByOwnerStartTime(ownerStartTime: Long): Flow<List<LocationEventDbModel>>

    @Query(
        "UPDATE tb_location_events SET displayName = :displayName WHERE latitude = :latitude AND longitude = :longitude"
    )
    fun updateLocationEventDbModelDisplayName(
        latitude: Double,
        longitude: Double,
        displayName: String
    )

    @Query("DELETE FROM tb_location_events WHERE ownerStartTime = :ownerStartTime")
    suspend fun deleteLocationEventDbModelsByOwnerStartTime(ownerStartTime: Long)
}
