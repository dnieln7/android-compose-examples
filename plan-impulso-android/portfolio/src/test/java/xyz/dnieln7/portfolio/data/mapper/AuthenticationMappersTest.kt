package xyz.dnieln7.portfolio.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.portfolio.fake.buildAuthenticationDTO

class AuthenticationMappersTest {

    @Test
    fun `GIVEN a AuthenticationDTO WHEN toDomain THEN get the expected Authentication`() {
        val authenticationDTO = buildAuthenticationDTO()
        val result = authenticationDTO.toDomain()

        with(result) {
            successful shouldBeEqualTo authenticationDTO.successful
            message shouldBeEqualTo authenticationDTO.message
            token shouldBeEqualTo authenticationDTO.token
        }
    }
}
