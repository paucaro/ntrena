package com.bourbon.ntrena.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bourbon.ntrena.WorkoutViewModel
import com.bourbon.ntrena.util.primary
import com.bourbon.ntrena.widgets.AddButton
import com.bourbon.ntrena.widgets.AddWorkout
import com.bourbon.ntrena.widgets.WorkoutSection

@Composable
fun Workout(
    workoutViewModel: WorkoutViewModel,
    navController: NavController
) {

    val showWorkoutForm by workoutViewModel.showWorkoutForm.collectAsState()

    val workoutName by workoutViewModel.workoutName.collectAsState()

    val workouts by workoutViewModel.workoutsList.collectAsState()

    Scaffold(
        floatingActionButton = {
            AddButton { workoutViewModel.toggleWorkoutForm() }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("H", fontSize = 92.sp, fontWeight = FontWeight.Bold, color = primary)
                    Column {
                        Text("ola", fontSize = 42.sp)
                        HorizontalDivider(color = primary)
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(workouts) { item ->
                        WorkoutSection(
                            workoutViewModel = workoutViewModel,
                            workout = item,
                            navController = navController
                        )
                    }
                }
            }
            if (showWorkoutForm) {
                AddWorkout(
                    workoutName,
                    onDismissRequest = { workoutViewModel.toggleWorkoutForm() },
                    onValueChange = { newName -> workoutViewModel.onWorkoutNameChange(newName) }
                ) {
                    workoutViewModel.addNewWorkout()
                }
            }
        }
    )
}