package soy.gabimoreno.danielnolasco.data.mapper

import soy.gabimoreno.danielnolasco.data.database.model.LocationEventDbModel
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent

fun LocationEvent.toDbModel(ownerStartTime: Long): LocationEventDbModel {
    return LocationEventDbModel(
        ownerStartTime = ownerStartTime,
        latitude = latitude,
        longitude = longitude,
        displayName = displayName,
    )
}

fun LocationEventDbModel.toDomain(): LocationEvent {
    return LocationEvent(
        latitude = latitude,
        longitude = longitude,
        displayName = displayName,
    )
}
