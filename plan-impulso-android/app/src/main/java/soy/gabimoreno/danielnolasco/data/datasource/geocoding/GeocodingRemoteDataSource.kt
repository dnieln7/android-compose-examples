package soy.gabimoreno.danielnolasco.data.datasource.geocoding

import arrow.core.Either
import soy.gabimoreno.danielnolasco.data.mapper.toDomain
import soy.gabimoreno.danielnolasco.data.server.GeoCodingApiService
import soy.gabimoreno.danielnolasco.domain.model.ReverseLocation

class GeocodingRemoteDataSource(private val geoCodingApiService: GeoCodingApiService) {

    suspend fun getReverseLocation(
        latitude: Double,
        longitude: Double
    ): Either<Throwable, ReverseLocation> {
        return Either.catch {
            geoCodingApiService.getReverseLocation(latitude, longitude).toDomain()
        }
    }
}
