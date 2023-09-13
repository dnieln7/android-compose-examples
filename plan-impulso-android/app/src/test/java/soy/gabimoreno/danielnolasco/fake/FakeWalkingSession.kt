package soy.gabimoreno.danielnolasco.fake

import soy.gabimoreno.danielnolasco.data.database.model.WalkingSessionDbModel
import soy.gabimoreno.danielnolasco.data.database.relation.CompleteWalkingSession
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

fun buildWalkingSession(): WalkingSession {
    return WalkingSession(
        startTime = 1000,
        endTime = 1500,
        duration = 500,
        locationEvents = buildLocationEvents(),
    )
}

fun buildWalkingSessionDbModel(): WalkingSessionDbModel {
    return WalkingSessionDbModel(
        startTime = 1000,
        endTime = 1500,
        duration = 500,
    )
}

fun buildCompleteWalkingSession(): CompleteWalkingSession {
    return CompleteWalkingSession(
        walkingSession = WalkingSessionDbModel(
            startTime = 1000,
            endTime = 1500,
            duration = 500,
        ),
        locationEvents = buildLocationEventDbModels(1000),
    )
}
