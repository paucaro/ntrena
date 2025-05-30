package com.bourbon.ntrena.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bourbon.ntrena.models.Converters
import com.bourbon.ntrena.models.Exercise
import com.bourbon.ntrena.models.Workout

@Database(
    entities = [
        Workout::class,
        Exercise::class
    ], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}