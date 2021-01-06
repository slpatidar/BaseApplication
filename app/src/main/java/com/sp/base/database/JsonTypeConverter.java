package com.sp.base.database;

import androidx.room.TypeConverter;

import java.nio.charset.StandardCharsets;

public class JsonTypeConverter {
    @TypeConverter
    public static byte[] encodeJsonResponse(String value) {
        return value.getBytes();
    }

    @TypeConverter
    public static String decodeJsonResponse(byte[] encodedValue) {
        return new String(encodedValue, StandardCharsets.UTF_8);
    }
}
