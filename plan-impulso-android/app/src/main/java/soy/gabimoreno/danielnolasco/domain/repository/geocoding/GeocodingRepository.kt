package soy.gabimoreno.danielnolasco.domain.repository.geocoding

import arrow.core.Either
import soy.gabimoreno.danielnolasco.domain.model.ReverseLocation

interface GeocodingRepository {
    suspend fun getReverseLocation(
        latitude: Double,
        longitude: Double
    ): Either<Throwable, ReverseLocation>
}
