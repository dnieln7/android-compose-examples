package soy.gabimoreno.danielnolasco.fake

import soy.gabimoreno.danielnolasco.data.database.model.LocationEventDbModel
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent

fun buildLocationEvent(): LocationEvent {
    return LocationEvent(
        latitude = 1.0,
        longitude = 2.0,
        displayName = "fake_display_name",
    )
}

fun buildLocationEventDbModel(): LocationEventDbModel {
    return LocationEventDbModel(
        ownerStartTime = FAKE_LOCATION_EVENT_START_TIME,
        latitude = 1.0,
        longitude = 2.0,
        displayName = "fake_display_name",
    )
}

fun buildLocationEvents(): List<LocationEvent> {
    val locationEvents = mutableListOf<LocationEvent>()

    for (i in 1..10) {
        locationEvents.add(
            LocationEvent(
                latitude = i.times(0.5),
                longitude = i.plus(1).times(0.5),
                displayName = "${i}_fake_display_name",
            )
        )
    }

    return locationEvents
}

fun buildLocationEventsWithNullDisplayName(): List<LocationEvent> {
    val locationEvents = mutableListOf<LocationEvent>()

    for (i in 1..10) {
        locationEvents.add(
            LocationEvent(
                latitude = i.times(0.5),
                longitude = i.plus(1).times(0.5),
                displayName = null,
            )
        )
    }

    return locationEvents
}

fun buildLocationEventDbModels(ownerStartTime: Long): List<LocationEventDbModel> {
    val locationEvents = mutableListOf<LocationEventDbModel>()

    for (i in 1..10) {
        locationEvents.add(
            LocationEventDbModel(
                ownerStartTime = ownerStartTime,
                latitude = i.times(0.5),
                longitude = i.plus(1).times(0.5),
                displayName = "${i}_fake_display_name",
            )
        )
    }

    return locationEvents
}

const val FAKE_LOCATION_EVENT_START_TIME = 1L
