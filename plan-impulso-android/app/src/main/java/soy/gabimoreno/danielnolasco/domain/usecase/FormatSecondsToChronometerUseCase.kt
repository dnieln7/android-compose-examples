package soy.gabimoreno.danielnolasco.domain.usecase

import java.util.*

class FormatSecondsToChronometerUseCase(private val locale: Locale) {

    operator fun invoke(seconds: Long): String {
        return if (seconds >= 0) {
            val hour = (seconds / SECONDS_IN_ONE_HOUR).toInt()
            val minute = (seconds % SECONDS_IN_ONE_HOUR / SECONDS_IN_ONE_MINUTE).toInt()
            val second = (seconds % SECONDS_IN_ONE_MINUTE).toInt()

            String.format(locale, HH_MM_SS_FORMAT, hour, minute, second)
        } else {
            ZERO_HH_MM_SS
        }
    }
}

private const val SECONDS_IN_ONE_HOUR = 3600F
private const val SECONDS_IN_ONE_MINUTE = 60F

private const val HH_MM_SS_FORMAT = "%02d:%02d:%02d"
private const val ZERO_HH_MM_SS = "00:00:00"
