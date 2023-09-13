package xyz.dnieln7.portfolio.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.portfolio.fake.buildPortfolioResponse

class ResultMappersTest {

    @Test
    fun `GIVEN a PortfolioResponse WHEN toResult THEN get the expected Result`() {
        val updateProjectResponse = buildPortfolioResponse()
        val result = updateProjectResponse.toResult()

        with(result) {
            successful shouldBeEqualTo successful
            message shouldBeEqualTo message
        }
    }
}
