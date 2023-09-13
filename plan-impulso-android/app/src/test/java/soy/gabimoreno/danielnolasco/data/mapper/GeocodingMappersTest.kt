package soy.gabimoreno.danielnolasco.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import soy.gabimoreno.danielnolasco.fake.buildGetReverseLocationResponse

class GeocodingMappersTest {

    @Test
    fun `GIVEN a GetReverseLocationResponse WHEN toDomain THEN get the expected ReverseLocation`() {
        val getReverseLocationResponse = buildGetReverseLocationResponse()
        val result = getReverseLocationResponse.toDomain()

        result.name shouldBeEqualTo getReverseLocationResponse.name
        result.displayName shouldBeEqualTo getReverseLocationResponse.displayName
    }
}
