package soy.gabimoreno.danielnolasco.domain.usecase

import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider

class RefreshDogsFromApiUseCase(private val preferencesProvider: PreferencesProvider) {

    operator fun invoke(
        currentTimeInMillis: Long,
        timeToRefreshInMillis: Long = ONE_HOUR
    ): Boolean {
        val lastTimeInMillis = preferencesProvider.getDogsLastFetchTimeInMillis()

        preferencesProvider.saveDogsLastFetchTimeInMillis(currentTimeInMillis)

        return currentTimeInMillis >= (lastTimeInMillis + timeToRefreshInMillis)
    }
}

private const val ONE_HOUR: Long = 60 * 60 * 1000
