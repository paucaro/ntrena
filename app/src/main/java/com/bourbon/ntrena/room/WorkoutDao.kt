package com.bourbon.ntrena.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bourbon.ntrena.models.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    suspend fun getAll(): List<Workout>

    @Insert
    suspend fun insertAll(vararg workout: Workout)

    @Update
    suspend fun updateWorkout(vararg workout: Workout)
}