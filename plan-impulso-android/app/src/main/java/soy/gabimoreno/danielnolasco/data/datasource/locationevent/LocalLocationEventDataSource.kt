package soy.gabimoreno.danielnolasco.data.datasource.locationevent

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import soy.gabimoreno.danielnolasco.data.database.dao.LocationEventDbModelDao
import soy.gabimoreno.danielnolasco.data.mapper.toDbModel
import soy.gabimoreno.danielnolasco.data.mapper.toDomain
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent

@OptIn(ExperimentalCoroutinesApi::class)
class LocalLocationEventDataSource(private val locationEventDbModelDao: LocationEventDbModelDao) {

    suspend fun saveLocationEvent(locationEvent: LocationEvent, ownerStartTime: Long) {
        val locationEventDbModel = locationEvent.toDbModel(ownerStartTime)

        locationEventDbModelDao.insertLocationEventDbModel(locationEventDbModel)
    }

    suspend fun getLocationEventsByOwnerStartTime(ownerStartTime: Long): List<LocationEvent> {
        return locationEventDbModelDao.getLocationEventDbModelsByOwnerStartTime(ownerStartTime)
            .map { it.toDomain() }
    }

    fun observeLocationEventsByOwnerStartTime(ownerStartTime: Long): Flow<List<LocationEvent>> {
        return locationEventDbModelDao.observeLocationEventDbModelsByOwnerStartTime(ownerStartTime)
            .mapLatest { locationEvents -> locationEvents.map { it.toDomain() } }
    }

    fun updateLocationEventDisplayName(latitude: Double, longitude: Double, displayName: String) {
        locationEventDbModelDao.updateLocationEventDbModelDisplayName(
            latitude,
            longitude,
            displayName,
        )
    }

    suspend fun deleteLocationEventsByOwnerStartTime(ownerStartTime: Long) {
        locationEventDbModelDao.deleteLocationEventDbModelsByOwnerStartTime(ownerStartTime)
    }
}
