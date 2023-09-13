package soy.gabimoreno.danielnolasco.data.mapper

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import soy.gabimoreno.danielnolasco.fake.buildCompleteWalkingSession
import soy.gabimoreno.danielnolasco.fake.buildWalkingSession
import soy.gabimoreno.danielnolasco.fake.buildWalkingSessionDbModel

class WalkingSessionMappersTest {

    @Test
    fun `GIVEN a WalkingSession WHEN toDbModel THEN get the expected WalkingSessionDbModel`() {
        val walkingSession = buildWalkingSession()
        val result = walkingSession.toDbModel()

        result.startTime shouldBeEqualTo walkingSession.startTime
        result.endTime shouldBeEqualTo walkingSession.endTime
        result.duration shouldBeEqualTo walkingSession.duration
    }

    @Test
    fun `GIVEN a WalkingSessionDbModel WHEN toDomain THEN get the expected WalkingSession`() {
        val walkingSessionDbModel = buildWalkingSessionDbModel()
        val result = walkingSessionDbModel.toDomain()

        result.startTime shouldBeEqualTo walkingSessionDbModel.startTime
        result.endTime shouldBeEqualTo walkingSessionDbModel.endTime
        result.duration shouldBeEqualTo walkingSessionDbModel.duration
        result.locationEvents.shouldBeEmpty()
    }

    @Test
    fun `GIVEN a CompleteWalkingSession WHEN toDomain THEN get the expected WalkingSession`() {
        val completeWalkingSession = buildCompleteWalkingSession()
        val result = completeWalkingSession.toDomain()

        result.startTime shouldBeEqualTo completeWalkingSession.walkingSession.startTime
        result.endTime shouldBeEqualTo completeWalkingSession.walkingSession.endTime
        result.duration shouldBeEqualTo completeWalkingSession.walkingSession.duration

        val locationEvent = completeWalkingSession.locationEvents.first()
        val locationEventResult = result.locationEvents.first()

        locationEventResult.latitude shouldBeEqualTo locationEvent.latitude
        locationEventResult.longitude shouldBeEqualTo locationEvent.longitude
        locationEventResult.displayName shouldBeEqualTo locationEvent.displayName
    }
}
