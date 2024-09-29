package com.baseapplication.database

import androidx.room.TypeConverter
import java.nio.charset.StandardCharsets

object JsonTypeConverter {
    @TypeConverter
    fun encodeJsonResponse(value: String): ByteArray {
        return value.toByteArray()
    }

    @TypeConverter
    fun decodeJsonResponse(encodedValue: ByteArray): String {
        return String((encodedValue), StandardCharsets.UTF_8)
    }
}
