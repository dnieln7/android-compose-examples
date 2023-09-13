package soy.gabimoreno.danielnolasco.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import soy.gabimoreno.danielnolasco.domain.model.ReverseLocation
import soy.gabimoreno.danielnolasco.domain.repository.geocoding.GeocodingRepository

class ProcessLocationUseCaseTest {

    private lateinit var useCase: ProcessLocationUseCase

    private val fakeReverseLocation = ReverseLocation(
        name = "Calle Isabel la Católica",
        displayName = "Calle Isabel la Católica, Cuauhtémoc, Ciudad de México, 06800, México"
    )

    private val fakeGeocodingRepository: GeocodingRepository = object : GeocodingRepository {

        override suspend fun getReverseLocation(
            latitude: Double,
            longitude: Double
        ): Either<Throwable, ReverseLocation> {
            val throwable = HttpException(
                Response.error<Void>(500, "".toResponseBody(null))
            )

            return if (latitude in -90.0..90.0 && longitude in -180.0..180.0) {
                fakeReverseLocation.right()
            } else {
                throwable.left()
            }
        }
    }

    @Before
    fun setUp() {
        useCase = ProcessLocationUseCase(fakeGeocodingRepository)
    }

    @Test
    fun `GIVEN valid latitude and longitude WHEN invoke THEN return location with displayName`() {
        runBlocking {
            val result = useCase(1.0, 2.0)
            val expectedResult = fakeReverseLocation.displayName

            result.displayName shouldBeEqualTo expectedResult
        }
    }

    @Test
    fun `GIVEN invalid latitude and longitude WHEN invoke THEN return a location without displayName`() {
        runBlocking {
            val result = useCase(10000000.0, -20000.0)

            result.displayName.shouldBeNull()
        }
    }
}
