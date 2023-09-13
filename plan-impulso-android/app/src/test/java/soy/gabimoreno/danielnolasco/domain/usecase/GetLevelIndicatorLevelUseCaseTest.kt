package soy.gabimoreno.danielnolasco.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class GetLevelIndicatorLevelUseCaseTest {

    private lateinit var useCase: GetLevelIndicatorLevelUseCase

    @Before
    fun setUp() {
        useCase = GetLevelIndicatorLevelUseCase()
    }

    @Test
    fun `GIVEN raw level is 1 WHEN invoke THEN level should be 0,20`() {
        val level = useCase(1)

        level shouldBeEqualTo 0.20F
    }

    @Test
    fun `GIVEN raw level is 2 WHEN invoke THEN level should be 0,40`() {
        val level = useCase(2)

        level shouldBeEqualTo 0.40F
    }

    @Test
    fun `GIVEN raw level is 3 WHEN invoke THEN level should be 0,60`() {
        val level = useCase(3)

        level shouldBeEqualTo 0.60F
    }

    @Test
    fun `GIVEN raw level is 4 WHEN invoke THEN level should be 0,80`() {
        val level = useCase(4)

        level shouldBeEqualTo 0.80F
    }

    @Test
    fun `GIVEN raw level is 5 WHEN invoke THEN level should be 1`() {
        val level = useCase(5)

        level shouldBeEqualTo 1F
    }
}
