package xyz.dnieln7.portfolio.data.local.converter

import androidx.room.TypeConverter

class StringListConverters {

    @TypeConverter
    fun fromStringToStringList(value: String): List<String> {
        return value.split("|")
    }

    @TypeConverter
    fun fromStringListToString(value: List<String>): String {
        return value.joinToString(separator = "|")
    }
}
