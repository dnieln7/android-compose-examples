package soy.gabimoreno.danielnolasco.data.repository.locationevent

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import soy.gabimoreno.danielnolasco.data.database.WalkingDatabase
import soy.gabimoreno.danielnolasco.data.datasource.locationevent.LocalLocationEventDataSource
import soy.gabimoreno.danielnolasco.fake.buildLocationEvents

@RunWith(AndroidJUnit4::class)
class DefaultLocationEventRepositoryTest {

    private lateinit var database: WalkingDatabase
    private lateinit var repository: DefaultLocationEventRepository

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()

        database = Room.inMemoryDatabaseBuilder(
            context,
            WalkingDatabase::class.java
        ).build()

        repository = DefaultLocationEventRepository(
            LocalLocationEventDataSource(database.locationEventDbModelDao())
        )
    }

    @Test
    fun given_an_existing_LocationEvent_when_getLocationEventsByOwnerStartTime_then_returned_list_should_not_be_empty() {
        val ownerStartTime = 0L
        val locationEvent = buildLocationEvents().first()

        runBlocking {
            repository.saveLocationEvent(locationEvent, ownerStartTime)

            val locationEvents = repository.getLocationEventsByOwnerStartTime(ownerStartTime)

            locationEvents.shouldNotBeEmpty()
        }
    }

    @Test
    fun given_an_existing_LocationEvent_when_observeLocationEventsByOwnerStartTime_then_returned_list_should_not_be_empty() {
        val ownerStartTime = 0L
        val locationEvent = buildLocationEvents().first()

        runBlocking {
            repository.saveLocationEvent(locationEvent, ownerStartTime)

            val locationEvents = repository.observeLocationEventsByOwnerStartTime(
                ownerStartTime
            ).first()

            locationEvents.shouldNotBeEmpty()
        }
    }

    @Test
    fun given_the_happy_path_when_updateLocationEventDisplayName_then_returned_object_should_have_new_display_name() {
        val ownerStartTime = 0L
        val newDisplayName = "NEW DISPLAY NAME"
        val locationEvent = buildLocationEvents().first()

        runBlocking {
            repository.saveLocationEvent(locationEvent, ownerStartTime)
            repository.updateLocationEventDisplayName(
                locationEvent.latitude,
                locationEvent.longitude,
                newDisplayName,
            )

            val newLocationEvent = repository.getLocationEventsByOwnerStartTime(ownerStartTime).first()

            newLocationEvent.displayName shouldBeEqualTo newDisplayName
        }
    }

    @Test
    fun given_the_happy_path_when_deleteLocationEventsByOwnerStartTime_then_returned_list_should_be_empty() {
        val ownerStartTime = 0L
        val locationEvent = buildLocationEvents().first()

        runBlocking {
            repository.saveLocationEvent(locationEvent, ownerStartTime)
            repository.deleteLocationEventsByOwnerStartTime(ownerStartTime)

            val locationEvents = repository.getLocationEventsByOwnerStartTime(ownerStartTime)

            locationEvents.shouldBeEmpty()
        }
    }

    @After
    fun teardown() {
        database.close()
    }
}
