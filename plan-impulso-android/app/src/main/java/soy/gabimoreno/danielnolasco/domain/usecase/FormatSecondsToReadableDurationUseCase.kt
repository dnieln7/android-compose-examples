package soy.gabimoreno.danielnolasco.domain.usecase

import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.res.R

class FormatSecondsToReadableDurationUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(seconds: Long): String {
        val hour = (seconds / SECONDS_IN_ONE_HOUR).toInt()
        val minute = (seconds % SECONDS_IN_ONE_HOUR / SECONDS_IN_ONE_MINUTE).toInt()
        val second = (seconds % SECONDS_IN_ONE_MINUTE).toInt()

        return if (hour > 0) {
            resourceProvider.getString(
                R.string.duration_hours_placeholder_minutes_placeholder_seconds_placeholder,
                hour,
                minute,
                second,
            )
        } else if (minute > 0) {
            resourceProvider.getString(
                R.string.duration_minutes_placeholder_seconds_placeholder,
                minute,
                second,
            )
        } else if (second > 0) {
            resourceProvider.getString(R.string.duration_seconds_placeholder, second)
        } else {
            resourceProvider.getString(R.string.duration_seconds_placeholder, 0)
        }
    }
}

private const val SECONDS_IN_ONE_HOUR = 3600F
private const val SECONDS_IN_ONE_MINUTE = 60F
