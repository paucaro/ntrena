package com.bourbon.ntrena.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bourbon.ntrena.WorkoutDetailPage
import com.bourbon.ntrena.WorkoutViewModel
import com.bourbon.ntrena.models.Workout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun WorkoutSection(
    workoutViewModel: WorkoutViewModel,
    workout: Workout,
    navController: NavController
) {
    var showExerciseForm by remember { mutableStateOf(false) }
    var exerciseName by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    val timeUnit by workoutViewModel.timeUnit.collectAsState()

    val showStartWorkout = remember { mutableStateOf(false) }
    val series = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                workout.exercises?.forEach { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        exercise.name?.let {
                            Text(
                                text = it,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = exercise.duration.toString(),
                            textAlign = TextAlign.End
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    AddButton(40.dp) { showExerciseForm = true }
                    ActionButton("Start", Icons.Filled.PlayArrow) { showStartWorkout.value = true }
                }
            }
            if (showExerciseForm) {
                AddExercise(
                    exercise = exerciseName,
                    duration = duration,
                    optionSelected = timeUnit,
                    options = workoutViewModel.options,
                    onDismissRequest = { showExerciseForm = false },
                    onExerciseValueChange = { exerciseName = it },
                    onDurationValueChange = { duration = it },
                    onMultiToggleSelect = { newTimeUnit -> workoutViewModel.onTimeUnitChange(newTimeUnit) },
                    onSave = { workoutViewModel.addNewExercise(
                        idWorkout = workout.id,
                        exerciseName = exerciseName,
                        duration = duration.toInt()
                    )
                        showExerciseForm = false
                        exerciseName = ""
                        duration = ""
                    }
                )
            }
            if (showStartWorkout.value) {
                StartWorkout(
                    series.value,
                    showStartWorkout,
                    onValueChange = { newValue -> series.value = newValue}
                ) {
                    val json = Json.encodeToString(workout)
                    navController.navigate(WorkoutDetailPage(
                        workoutJson = json,
                        series = series.value.toInt()
                    ))
                }
            }
        }

        workout.name?.let {
            Text(
                text = it.uppercase(),
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = (-16).dp)
                    .background(Color.White)
                    .padding(start = 8.dp, end = 16.dp)
            )
        }
    }
}