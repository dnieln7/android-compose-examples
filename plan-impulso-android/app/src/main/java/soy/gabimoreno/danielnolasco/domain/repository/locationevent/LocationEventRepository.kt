package soy.gabimoreno.danielnolasco.domain.repository.locationevent

import kotlinx.coroutines.flow.Flow
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent

interface LocationEventRepository {
    suspend fun saveLocationEvent(locationEvent: LocationEvent, ownerStartTime: Long)
    suspend fun getLocationEventsByOwnerStartTime(ownerStartTime: Long): List<LocationEvent>
    fun observeLocationEventsByOwnerStartTime(ownerStartTime: Long): Flow<List<LocationEvent>>
    fun updateLocationEventDisplayName(latitude: Double, longitude: Double, displayName: String)
    suspend fun deleteLocationEventsByOwnerStartTime(ownerStartTime: Long)
}
