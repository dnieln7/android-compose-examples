package soy.gabimoreno.danielnolasco.data.server

import retrofit2.http.GET
import retrofit2.http.Query
import soy.gabimoreno.danielnolasco.data.server.dto.GetReverseLocationResponse

interface GeoCodingApiService {

    @GET("reverse")
    suspend fun getReverseLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("zoom") zoom: Int = DEFAULT_ZOOM,
        @Query("format") format: String = DEFAULT_FORMAT,
    ): GetReverseLocationResponse
}

private const val DEFAULT_ZOOM = 17
private const val DEFAULT_FORMAT = "jsonv2"
