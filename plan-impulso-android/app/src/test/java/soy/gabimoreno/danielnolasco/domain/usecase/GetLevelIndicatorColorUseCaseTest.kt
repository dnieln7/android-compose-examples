package soy.gabimoreno.danielnolasco.domain.usecase

import androidx.compose.ui.graphics.Color
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class GetLevelIndicatorColorUseCaseTest {

    private lateinit var useCase: GetLevelIndicatorColorUseCase

    @Before
    fun setUp() {
        useCase = GetLevelIndicatorColorUseCase()
    }

    @Test
    fun `GIVEN level is good WHEN invoke THEN color should be green`() {
        val color = useCase(0.80F)

        color shouldBeEqualTo Color.Green
    }

    @Test
    fun `GIVEN level is average WHEN invoke THEN color should be yellow`() {
        val color = useCase(0.60F)

        color shouldBeEqualTo Color.Yellow
    }

    @Test
    fun `GIVEN level is neither good or average WHEN invoke THEN color should be red`() {
        val color = useCase(0.10F)

        color shouldBeEqualTo Color.Red
    }
}
