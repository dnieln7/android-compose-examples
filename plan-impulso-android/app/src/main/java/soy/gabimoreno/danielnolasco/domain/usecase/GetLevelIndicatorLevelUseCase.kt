package soy.gabimoreno.danielnolasco.domain.usecase

class GetLevelIndicatorLevelUseCase {

    operator fun invoke(rawLevel: Int): Float {
        return rawLevel.div(MAX_RAW_LEVEL)
    }
}

private const val MAX_RAW_LEVEL = 5f
