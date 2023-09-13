package xyz.dnieln7.portfolio.data.datasource

import arrow.core.Either
import xyz.dnieln7.portfolio.data.mapper.toDomain
import xyz.dnieln7.portfolio.data.remote.PortfolioApiService
import xyz.dnieln7.portfolio.data.remote.request.LoginRequest
import xyz.dnieln7.portfolio.domain.model.Authentication

class RemoteAuthDataSource(private val portfolioApiService: PortfolioApiService) {

    suspend fun login(email: String, password: String): Either<Throwable, Authentication> {
        val loginRequest = LoginRequest(email, password)

        return Either.catch { portfolioApiService.login(loginRequest).toDomain() }
    }
}
