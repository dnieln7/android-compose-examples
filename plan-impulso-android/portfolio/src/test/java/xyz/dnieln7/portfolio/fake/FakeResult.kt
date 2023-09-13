package xyz.dnieln7.portfolio.fake

import xyz.dnieln7.portfolio.data.remote.response.PortfolioResponse
import xyz.dnieln7.portfolio.domain.model.Result

fun buildSuccessfulResult(): Result {
    return Result(
        successful = true,
        message = "test_message",
    )
}

fun buildUnSuccessfulResult(): Result {
    return Result(
        successful = false,
        message = "test_message",
    )
}

fun buildPortfolioResponse(): PortfolioResponse {
    return PortfolioResponse(
        successful = true,
        message = "test_message",
    )
}
