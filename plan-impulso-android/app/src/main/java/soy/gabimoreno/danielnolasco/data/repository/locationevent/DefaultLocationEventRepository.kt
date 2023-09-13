package soy.gabimoreno.danielnolasco.data.repository.locationevent

import kotlinx.coroutines.flow.Flow
import soy.gabimoreno.danielnolasco.data.datasource.locationevent.LocalLocationEventDataSource
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository

class DefaultLocationEventRepository(
    private val localLocationEventDataSource: LocalLocationEventDataSource,
) : LocationEventRepository {

    override suspend fun saveLocationEvent(locationEvent: LocationEvent, ownerStartTime: Long) {
        localLocationEventDataSource.saveLocationEvent(locationEvent, ownerStartTime)
    }

    override suspend fun getLocationEventsByOwnerStartTime(ownerStartTime: Long): List<LocationEvent> {
        return localLocationEventDataSource.getLocationEventsByOwnerStartTime(ownerStartTime)
    }

    override fun observeLocationEventsByOwnerStartTime(ownerStartTime: Long): Flow<List<LocationEvent>> {
        return localLocationEventDataSource.observeLocationEventsByOwnerStartTime(ownerStartTime)
    }

    override fun updateLocationEventDisplayName(
        latitude: Double,
        longitude: Double,
        displayName: String
    ) {
        localLocationEventDataSource.updateLocationEventDisplayName(
            latitude,
            longitude,
            displayName,
        )
    }

    override suspend fun deleteLocationEventsByOwnerStartTime(ownerStartTime: Long) {
        localLocationEventDataSource.deleteLocationEventsByOwnerStartTime(ownerStartTime)
    }
}
