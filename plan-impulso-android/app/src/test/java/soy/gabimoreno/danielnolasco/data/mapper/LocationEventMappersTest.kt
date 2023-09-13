package soy.gabimoreno.danielnolasco.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import soy.gabimoreno.danielnolasco.fake.FAKE_LOCATION_EVENT_START_TIME
import soy.gabimoreno.danielnolasco.fake.buildLocationEvent
import soy.gabimoreno.danielnolasco.fake.buildLocationEventDbModel

class LocationEventMappersTest {

    @Test
    fun `GIVEN a LocationEvent WHEN toDbModel THEN get the expected LocationEventDbModel`() {
        val locationEvent = buildLocationEvent()
        val result = locationEvent.toDbModel(FAKE_LOCATION_EVENT_START_TIME)

        result.latitude shouldBeEqualTo locationEvent.latitude
        result.longitude shouldBeEqualTo locationEvent.longitude
        result.displayName shouldBeEqualTo locationEvent.displayName
        result.ownerStartTime shouldBeEqualTo FAKE_LOCATION_EVENT_START_TIME
    }

    @Test
    fun `GIVEN a LocationEventDbModel WHEN toDomain THEN get the expected LocationEvent`() {
        val locationEventDbModel = buildLocationEventDbModel()
        val result = locationEventDbModel.toDomain()

        result.latitude shouldBeEqualTo locationEventDbModel.latitude
        result.longitude shouldBeEqualTo locationEventDbModel.longitude
        result.displayName shouldBeEqualTo locationEventDbModel.displayName
    }
}
