package soy.gabimoreno.danielnolasco.domain.extensions

import soy.gabimoreno.danielnolasco.domain.model.Dog

val Dog.isPlayful get() = playfulness >= PLAYFUL_MINIMUM_LEVEL

private const val PLAYFUL_MINIMUM_LEVEL = 3
