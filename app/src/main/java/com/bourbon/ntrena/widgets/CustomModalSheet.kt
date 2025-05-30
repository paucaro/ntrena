package com.bourbon.ntrena.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkout(
    workout: String,
    onDismissRequest: () -> Unit,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                text = workout,
                label = "Workout name",
                icon = Icons.Filled.Create,
                onValueChange = onValueChange
            )
            ActionButton("Save", Icons.Filled.ThumbUp) { onClick() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExercise(
    exercise: String,
    duration: String,
    optionSelected: String,
    options: Array<String>,
    onDismissRequest: () -> Unit,
    onExerciseValueChange: (String) -> Unit,
    onDurationValueChange: (String) -> Unit,
    onMultiToggleSelect: (String) -> Unit,
    onSave: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                text = exercise,
                label = "Exercise name",
                icon = Icons.Filled.Create,
                onValueChange = onExerciseValueChange
            )
            TimeInput(
                inputValue = duration,
                optionSelected = optionSelected,
                options = options,
                onValueChange = onDurationValueChange,
                onMultiToggleSelect = onMultiToggleSelect
            )
            ActionButton("Save", Icons.Filled.ThumbUp) { onSave() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartWorkout(
    series: String,
    showWorkoutForm: MutableState<Boolean>,
    onValueChange: (String) -> Unit,
    startWorkoutAction: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            showWorkoutForm.value = false
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NumberTextField(
                inputValue = series,
                label = "Series",
                onValueChange = onValueChange
            )
            ActionButton("Save", Icons.Filled.ThumbUp) {
                startWorkoutAction()
            }
        }
    }
}