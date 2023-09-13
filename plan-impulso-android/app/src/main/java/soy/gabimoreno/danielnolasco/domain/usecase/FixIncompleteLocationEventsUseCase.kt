package soy.gabimoreno.danielnolasco.domain.usecase

import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository

class FixIncompleteLocationEventsUseCase(
    private val locationEventRepository: LocationEventRepository,
    private val processLocationUseCase: ProcessLocationUseCase
) {

    suspend operator fun invoke(locationEvents: List<LocationEvent>): List<LocationEvent> {
        val fixedLocationEvents = mutableListOf<LocationEvent>()

        locationEvents.forEach { locationEvent ->
            if (locationEvent.displayName == null) {
                val result = processLocationUseCase(
                    locationEvent.latitude,
                    locationEvent.longitude,
                )

                if (result.displayName != null) {
                    fixedLocationEvents.add(result)

                    locationEventRepository.updateLocationEventDisplayName(
                        result.latitude,
                        result.longitude,
                        result.displayName
                    )
                }
            }
        }

        return fixedLocationEvents
    }
}
