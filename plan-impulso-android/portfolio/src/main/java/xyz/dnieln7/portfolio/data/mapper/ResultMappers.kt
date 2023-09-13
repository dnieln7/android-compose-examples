package xyz.dnieln7.portfolio.data.mapper

import xyz.dnieln7.portfolio.data.remote.response.PortfolioResponse
import xyz.dnieln7.portfolio.domain.model.Result

fun PortfolioResponse.toResult(): Result {
    return Result(
        successful = successful,
        message = message,
    )
}
