package xyz.dnieln7.portfolio.domain.usecase

import javax.inject.Inject
import retrofit2.HttpException
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R
import xyz.dnieln7.portfolio.data.remote.PortfolioApiServiceStatus

class GetPortfolioApiServiceErrorUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    operator fun invoke(throwable: Throwable): String {
        return if (
            throwable is HttpException &&
            throwable.code() == PortfolioApiServiceStatus.NOT_AUTHENTICATED.code
        ) {
            resourceProvider.getString(R.string.not_authenticated)
        } else {
            return getErrorFromThrowableUseCase(throwable)
        }
    }
}
