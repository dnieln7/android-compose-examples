package soy.gabimoreno.danielnolasco.domain.usecase

import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R

class GetDogApiServiceLoginErrorUseCaseTest {

    private lateinit var useCase: GetDogApiServiceErrorUseCase

    private val fakeResourceProvider = object : ResourceProvider {
        override fun getString(id: Int): String {
            return when (id) {
                R.string.not_authenticated -> "Not authenticated"
                R.string.unknown_error -> "Unknown error"
                else -> ""
            }
        }

        override fun getString(id: Int, vararg args: Any): String {
            return ""
        }
    }

    @Before
    fun setUp() {
        val getErrorFromThrowableUseCase = GetErrorFromThrowableUseCase(fakeResourceProvider)

        useCase = GetDogApiServiceErrorUseCase(fakeResourceProvider, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN HttpException with 400 code WHEN invoke THEN returned string should be not_authenticated`() {
        val throwable = HttpException(
            Response.error<Void>(400, "".toResponseBody(null))
        )

        val result = useCase(throwable)
        val expectedResult = fakeResourceProvider.getString(R.string.not_authenticated)

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN throwable with localizedMessage WHEN invoke THEN returned string should be localizedMessage`() {
        val throwable = Exception("Server is off")

        val result = useCase(throwable)

        result shouldBeEqualTo throwable.localizedMessage!!
    }

    @Test
    fun `GIVEN throwable without localizedMessage WHEN invoke THEN returned string should be unknown_error`() {
        val throwable = Exception()

        val result = useCase(throwable)
        val expectedResult = fakeResourceProvider.getString(R.string.unknown_error)

        result shouldBeEqualTo expectedResult
    }
}
