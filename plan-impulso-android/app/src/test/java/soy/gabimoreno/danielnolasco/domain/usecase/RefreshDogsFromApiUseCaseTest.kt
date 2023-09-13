package soy.gabimoreno.danielnolasco.domain.usecase

import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider

class RefreshDogsFromApiUseCaseTest {

    private lateinit var useCase: RefreshDogsFromApiUseCase

    private val preferencesProvider = object : PreferencesProvider {
        private var dogsLastFetchTime = 0L

        override fun saveDogsLastFetchTimeInMillis(timeInMillis: Long) {
            dogsLastFetchTime = timeInMillis
        }

        override fun getDogsLastFetchTimeInMillis(): Long {
            return dogsLastFetchTime
        }

        override fun saveDogsLastOffset(offset: Int): Boolean {
            return false
        }

        override fun getDogsLastOffset(): Int {
            return 0
        }
    }

    @Before
    fun setUp() {
        useCase = RefreshDogsFromApiUseCase(preferencesProvider)
    }

    @Test
    fun `GIVEN currentTimeInMillis greater or equal to lastTime WHEN invoke THEN return true`() {
        val twoHours = 120 * 60 * 1000L
        val result = useCase(twoHours)

        result shouldBe true
    }

    @Test
    fun `GIVEN currentTimeInMillis lower than lastTime WHEN invoke THEN return false`() {
        val halfAnHour = 30 * 60 * 1000L
        val result = useCase(halfAnHour)

        result shouldBe false
    }
}
