package soy.gabimoreno.danielnolasco.domain.usecase

import androidx.compose.ui.graphics.Color

class GetLevelIndicatorColorUseCase(
    private val requiredGoodLevel: Float = GOOD_LEVEL,
    private val requiredAverageLevel: Float = AVERAGE_LEVEL,
) {

    operator fun invoke(level: Float): Color {
        return if (level >= requiredGoodLevel) {
            Color.Green
        } else if (level >= requiredAverageLevel) {
            Color.Yellow
        } else {
            Color.Red
        }
    }
}

private const val GOOD_LEVEL = 0.80f
private const val AVERAGE_LEVEL = 0.60f
