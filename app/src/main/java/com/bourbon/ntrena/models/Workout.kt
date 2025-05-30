package com.bourbon.ntrena.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity
@Serializable
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "series") val series: Int?,
    @ColumnInfo(name = "exercises") val exercises: List<Exercise>?
)

@Entity
@Serializable
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "duration") val duration: Int?,
)

class Converters {

    @TypeConverter
    fun fromExerciseList(value: List<Exercise>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toExerciseList(value: String?): List<Exercise>? {
        return value?.let { Json.decodeFromString(it) }
    }
}