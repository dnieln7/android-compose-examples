package soy.gabimoreno.danielnolasco.domain.usecase

import arrow.core.Either
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository

class DeleteWalkingSessionAndRelatedDataUseCase(
    private val walkingSessionRepository: WalkingSessionRepository,
    private val locationEventRepository: LocationEventRepository,
) {
    suspend operator fun invoke(startTime: Long): Boolean {
        return Either.catch {
            locationEventRepository.deleteLocationEventsByOwnerStartTime(startTime)
            walkingSessionRepository.deleteWalkingSessionsByStartTime(startTime)
        }.isRight()
    }
}
