package soy.gabimoreno.danielnolasco.framework.location

sealed class UpdatedLocation {
    class NewLocation(val latitude: Double, val longitude: Double) : UpdatedLocation()
    object EmptyLocation : UpdatedLocation()
}
