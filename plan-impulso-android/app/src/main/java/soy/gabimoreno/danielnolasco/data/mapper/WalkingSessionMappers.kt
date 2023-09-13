package soy.gabimoreno.danielnolasco.data.mapper

import soy.gabimoreno.danielnolasco.data.database.model.WalkingSessionDbModel
import soy.gabimoreno.danielnolasco.data.database.relation.CompleteWalkingSession
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

fun WalkingSession.toDbModel(): WalkingSessionDbModel {
    return WalkingSessionDbModel(
        startTime = startTime,
        endTime = endTime,
        duration = duration,
    )
}

fun WalkingSessionDbModel.toDomain(): WalkingSession {
    return WalkingSession(
        startTime = startTime,
        endTime = endTime,
        duration = duration,
        locationEvents = emptyList(),
    )
}

fun CompleteWalkingSession.toDomain(): WalkingSession {
    return WalkingSession(
        startTime = walkingSession.startTime,
        endTime = walkingSession.endTime,
        duration = walkingSession.duration,
        locationEvents = locationEvents.map { it.toDomain() }
    )
}
