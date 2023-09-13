package soy.gabimoreno.danielnolasco.domain.extensions

import soy.gabimoreno.danielnolasco.domain.model.Cat

val Cat.isPlayful get() = playfulness >= PLAYFUL_MINIMUM_LEVEL

private const val PLAYFUL_MINIMUM_LEVEL = 4
