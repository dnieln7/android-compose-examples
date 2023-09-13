package soy.gabimoreno.danielnolasco.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import soy.gabimoreno.danielnolasco.data.database.model.LocationEventDbModel
import soy.gabimoreno.danielnolasco.data.database.model.WalkingSessionDbModel

data class CompleteWalkingSession(
    @Embedded
    val walkingSession: WalkingSessionDbModel,
    @Relation(
        parentColumn = "startTime",
        entityColumn = "ownerStartTime"
    )
    val locationEvents: List<LocationEventDbModel>
)
