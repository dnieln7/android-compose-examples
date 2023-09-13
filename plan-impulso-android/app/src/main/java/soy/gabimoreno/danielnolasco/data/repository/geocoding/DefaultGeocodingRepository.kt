package soy.gabimoreno.danielnolasco.data.repository.geocoding

import arrow.core.Either
import soy.gabimoreno.danielnolasco.data.datasource.geocoding.GeocodingRemoteDataSource
import soy.gabimoreno.danielnolasco.domain.model.ReverseLocation
import soy.gabimoreno.danielnolasco.domain.repository.geocoding.GeocodingRepository

class DefaultGeocodingRepository(private val geocodingRemoteDataSource: GeocodingRemoteDataSource) :
    GeocodingRepository {

    override suspend fun getReverseLocation(
        latitude: Double,
        longitude: Double
    ): Either<Throwable, ReverseLocation> {
        return geocodingRemoteDataSource.getReverseLocation(latitude, longitude)
    }
}
