package com.bourbon.ntrena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bourbon.ntrena.models.Exercise
import com.bourbon.ntrena.models.Workout
import com.bourbon.ntrena.room.WorkoutDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val dao: WorkoutDao
): ViewModel() {

    val options = arrayOf("MIN", "SEC")

    private val _showWorkoutForm = MutableStateFlow(false)
    val showWorkoutForm: StateFlow<Boolean> = _showWorkoutForm

    private val _workoutsList = MutableStateFlow(emptyList<Workout>())
    val workoutsList: StateFlow<List<Workout>> = _workoutsList

    private val _workoutName = MutableStateFlow("")
    val workoutName: StateFlow<String> = _workoutName

    private val _timeUnit = MutableStateFlow(options.first())
    val timeUnit: StateFlow<String> = _timeUnit

    init {
        viewModelScope.launch {
            _workoutsList.value = dao.getAll()
            println(_workoutsList.value)
        }
    }

    fun toggleWorkoutForm() {
        _showWorkoutForm.value = !_showWorkoutForm.value
    }

    fun onWorkoutNameChange(newName: String) {
        _workoutName.value = newName
    }

    fun onTimeUnitChange(newTimeUnit: String) {
        _timeUnit.value = newTimeUnit
    }

    fun addNewWorkout() {
        val newWorkout = Workout(
            name = _workoutName.value,
            series = 1,
            exercises = emptyList()
        )
        viewModelScope.launch {
            dao.insertAll(newWorkout)
            _workoutsList.value += newWorkout
            _showWorkoutForm.value = false
            _workoutName.value = ""
        }
    }

    fun addNewExercise(idWorkout: Int, exerciseName: String, duration: Int) {
        val durationInSec: Int = when(_timeUnit.value) {
            "MIN" -> duration * 60
            else -> duration
        }

        val newExercise = Exercise(
            name = exerciseName,
            duration = durationInSec
        )

        viewModelScope.launch {
            val updatedWorkouts = _workoutsList.value.map { workout ->
                if (workout.id == idWorkout) {
                    val updatedExercises = workout.exercises?.plus(newExercise)
                    workout.copy(exercises = updatedExercises)
                } else {
                    workout
                }
            }

            val updatedWorkout = updatedWorkouts.first { it.id == idWorkout }
            dao.updateWorkout(updatedWorkout)

            _workoutsList.value = updatedWorkouts
            _timeUnit.value = options.first()
        }

    }
}