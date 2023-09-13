package xyz.dnieln7.portfolio.domain.repository

import arrow.core.Either
import org.joda.time.DateTime

interface AuthRepository {
    suspend fun login(email: String, password: String): Either<Throwable, DateTime>
}
