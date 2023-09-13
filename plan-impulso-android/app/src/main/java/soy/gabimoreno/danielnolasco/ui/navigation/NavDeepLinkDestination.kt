package soy.gabimoreno.danielnolasco.ui.navigation

import androidx.navigation.navDeepLink

abstract class NavDeepLinkDestination(
    rawRoute: String,
    rawArgs: List<NavArg>,
    rawDeepLinks: List<String>,
) : NavArgsDestination(rawRoute, rawArgs) {

    val deepLinks = rawDeepLinks.map { rawLink ->
        val link = if (rawArgs.isEmpty()) {
            rawLink
        } else {
            rawLink.plus("/").plus(
                rawArgs.joinToString("/") { "{${it.key}}" }
            )
        }

        navDeepLink { uriPattern = link }
    }
}
