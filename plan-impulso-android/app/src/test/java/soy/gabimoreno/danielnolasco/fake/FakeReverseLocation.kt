package soy.gabimoreno.danielnolasco.fake

import soy.gabimoreno.danielnolasco.data.server.dto.GetReverseLocationResponse

fun buildGetReverseLocationResponse(): GetReverseLocationResponse {
    return GetReverseLocationResponse(
        name = "fake_name",
        displayName = "fake_display_name",
    )
}
