package soy.gabimoreno.danielnolasco.domain.model

data class WalkingSession(
    val startTime: Long,
    val endTime: Long?,
    val duration: Long,
    val locationEvents: List<LocationEvent>
)
