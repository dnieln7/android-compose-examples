package xyz.dnieln7.core.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.res.R

class GetErrorFromThrowableUseCaseTest {

    private lateinit var useCase: GetErrorFromThrowableUseCase

    private val fakeResourceProvider = object : ResourceProvider {
        override fun getString(id: Int): String {
            return when (id) {
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
        useCase = GetErrorFromThrowableUseCase(fakeResourceProvider)
    }

    @Test
    fun `GIVEN throwable with localizedMessage WHEN invoke THEN returned string should be localizedMessage`() {
        val errorMessage = "Error message"
        val throwable = Exception(errorMessage)

        val result = useCase(throwable)

        result shouldBeEqualTo errorMessage
    }

    @Test
    fun `GIVEN throwable without localizedMessage WHEN invoke THEN returned string should be unknown_error`() {
        val throwable = Exception()
        val expectedResult = fakeResourceProvider.getString(R.string.unknown_error)

        val result = useCase(throwable)

        result shouldBeEqualTo expectedResult
    }
}
