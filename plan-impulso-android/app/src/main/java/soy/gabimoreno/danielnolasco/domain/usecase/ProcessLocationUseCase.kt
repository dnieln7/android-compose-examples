package soy.gabimoreno.danielnolasco.domain.usecase

import javax.inject.Inject
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.domain.repository.geocoding.GeocodingRepository

class ProcessLocationUseCase @Inject constructor(
    private val geocodingRepository: GeocodingRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): LocationEvent {
        return geocodingRepository.getReverseLocation(latitude, longitude).fold(
            {
                LocationEvent(latitude, longitude, null)
            },
            {
                LocationEvent(latitude, longitude, it.displayName)
            }
        )
    }
}
