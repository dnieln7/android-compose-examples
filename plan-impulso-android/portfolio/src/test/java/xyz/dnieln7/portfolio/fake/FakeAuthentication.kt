package xyz.dnieln7.portfolio.fake

import xyz.dnieln7.portfolio.data.remote.dto.AuthenticationDTO
import xyz.dnieln7.portfolio.domain.model.Authentication

fun buildAuthenticationDTO(): AuthenticationDTO {
    return AuthenticationDTO(
        successful = true,
        message = "test_message",
        token = "test_token"
    )
}

fun buildSuccessfulAuthentication(): Authentication {
    return Authentication(
        successful = true,
        message = "test_message",
        token = "test_token"
    )
}

fun buildUnSuccessfulAuthentication(): Authentication {
    return Authentication(
        successful = false,
        message = "test_message",
        token = "test_token"
    )
}
