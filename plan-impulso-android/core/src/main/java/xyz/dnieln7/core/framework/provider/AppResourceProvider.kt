package xyz.dnieln7.core.framework.provider

import android.content.Context
import xyz.dnieln7.core.domain.provider.ResourceProvider

class AppResourceProvider(private val context: Context) : ResourceProvider {

    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }
}
