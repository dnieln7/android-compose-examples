package xyz.dnieln7.core.domain.usecase

import javax.inject.Inject
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.res.R

class GetErrorFromThrowableUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
) {

    operator fun invoke(throwable: Throwable): String {
        return throwable.localizedMessage ?: resourceProvider.getString(
            R.string.unknown_error
        )
    }
}
