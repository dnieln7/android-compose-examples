package xyz.dnieln7.portfolio.data.mapper

import xyz.dnieln7.portfolio.data.remote.dto.AuthenticationDTO
import xyz.dnieln7.portfolio.domain.model.Authentication

fun AuthenticationDTO.toDomain(): Authentication {
    return Authentication(
        successful = successful,
        message = message,
        token = token,
    )
}
