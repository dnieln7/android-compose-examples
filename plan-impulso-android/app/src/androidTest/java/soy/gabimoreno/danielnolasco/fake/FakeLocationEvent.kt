package soy.gabimoreno.danielnolasco.fake

import soy.gabimoreno.danielnolasco.domain.model.LocationEvent

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
