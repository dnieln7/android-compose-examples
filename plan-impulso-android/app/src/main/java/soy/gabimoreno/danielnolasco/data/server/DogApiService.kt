package soy.gabimoreno.danielnolasco.data.server

import retrofit2.http.GET
import retrofit2.http.Query
import soy.gabimoreno.danielnolasco.data.server.dto.GetDogsResponse

interface DogApiService {

    @GET("dogs?min_weight=1")
    suspend fun getDogs(@Query("offset") offset: Int): List<GetDogsResponse>

    @GET("dogs")
    suspend fun getDogsByName(@Query("name") name: String): List<GetDogsResponse>
}

enum class DogApiServiceCode(val code: Int) {
    NOT_AUTHENTICATED(400)
}
