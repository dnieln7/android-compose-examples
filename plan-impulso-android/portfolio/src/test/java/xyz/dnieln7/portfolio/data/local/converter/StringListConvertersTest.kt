package xyz.dnieln7.portfolio.data.local.converter

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class StringListConvertersTest {

    private lateinit var converters: StringListConverters

    @Before
    fun setUp() {
        converters = StringListConverters()
    }

    @Test
    fun `GIVEN a stringList WHEN fromStringListToString THEN return the elements of stringList separated by a vertical line`() {
        val stringList = listOf("item_1", "item_2", "item_3")
        val expectedResult = "item_1|item_2|item_3"

        val result = converters.fromStringListToString(stringList)

        result shouldBeEqualTo expectedResult
    }

    @Test
    fun `GIVEN a string WHEN fromStringListToString THEN return a list with the elements of string that were separated by a vertical line`() {
        val string = "item_1|item_2|item_3"
        val expectedResult = listOf("item_1", "item_2", "item_3")

        val result = converters.fromStringToStringList(string)

        result shouldBeEqualTo expectedResult
    }
}
