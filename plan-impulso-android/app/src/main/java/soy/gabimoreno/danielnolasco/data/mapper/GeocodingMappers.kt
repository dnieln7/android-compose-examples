package soy.gabimoreno.danielnolasco.data.mapper

import soy.gabimoreno.danielnolasco.data.server.dto.GetReverseLocationResponse
import soy.gabimoreno.danielnolasco.domain.model.ReverseLocation

fun GetReverseLocationResponse.toDomain(): ReverseLocation {
    return ReverseLocation(
        name = name,
        displayName = displayName,
    )
}
