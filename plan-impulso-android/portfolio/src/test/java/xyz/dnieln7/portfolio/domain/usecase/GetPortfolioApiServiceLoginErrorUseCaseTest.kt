package xyz.dnieln7.portfolio.domain.usecase

import io.mockk.every
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.extensions.verifyOnce

class GetPortfolioApiServiceLoginErrorUseCaseTest {

    private lateinit var useCase: GetPortfolioApiServiceErrorUseCase
    private val resourceProvider = relaxedMockk<ResourceProvider>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    @Before
    fun setUp() {
        useCase = GetPortfolioApiServiceErrorUseCase(resourceProvider, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN HttpException with 401 code WHEN invoke THEN returned string should be Not authenticated`() {
        every { resourceProvider.getString(any()) } returns "Not authenticated"

        val throwable = HttpException(
            Response.error<Void>(401, "".toResponseBody(null))
        )

        val result = useCase(throwable)
        val expectedResult = resourceProvider.getString(R.string.not_authenticated)

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN a throwable WHEN invoke THEN getErrorFromThrowableUseCase_invoke should be called once`() {
        val throwable = Exception("Error")

        useCase(throwable)

        verifyOnce {
            getErrorFromThrowableUseCase.invoke(throwable)
        }
    }
}
