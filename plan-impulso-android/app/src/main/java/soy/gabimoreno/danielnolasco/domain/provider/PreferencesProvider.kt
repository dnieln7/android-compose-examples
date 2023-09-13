package soy.gabimoreno.danielnolasco.domain.provider

interface PreferencesProvider {
    fun saveDogsLastFetchTimeInMillis(timeInMillis: Long)
    fun getDogsLastFetchTimeInMillis(): Long
    fun saveDogsLastOffset(offset: Int): Boolean
    fun getDogsLastOffset(): Int
}
