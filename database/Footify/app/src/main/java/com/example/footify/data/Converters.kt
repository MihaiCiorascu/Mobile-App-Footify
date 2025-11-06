package com.example.footify.data

import androidx.room.TypeConverter
import com.example.footify.model.Position
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromPosition(position: Position): String {
        return position.name
    }

    @TypeConverter
    fun toPosition(position: String): Position {
        return Position.valueOf(position)
    }
}

