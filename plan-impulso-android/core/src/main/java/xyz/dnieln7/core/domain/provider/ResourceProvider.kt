package xyz.dnieln7.core.domain.provider

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
    fun getString(id: Int, vararg args: Any): String
}
