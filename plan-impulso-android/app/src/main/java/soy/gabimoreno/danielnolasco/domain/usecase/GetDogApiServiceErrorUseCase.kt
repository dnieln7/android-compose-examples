package soy.gabimoreno.danielnolasco.domain.usecase

import retrofit2.HttpException
import soy.gabimoreno.danielnolasco.data.server.DogApiServiceCode
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R

class GetDogApiServiceErrorUseCase(
    private val resourceProvider: ResourceProvider,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase
) {

    operator fun invoke(throwable: Throwable): String {
        return if (throwable is HttpException && throwable.code() == DogApiServiceCode.NOT_AUTHENTICATED.code) {
            resourceProvider.getString(R.string.not_authenticated)
        } else {
            return getErrorFromThrowableUseCase(throwable)
        }
    }
}
