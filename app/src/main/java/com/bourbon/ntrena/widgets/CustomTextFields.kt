package com.bourbon.ntrena.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bourbon.ntrena.util.primary

@Composable
fun CustomTextField(text: String, label: String, icon: ImageVector, onValueChange: (String) -> Unit) {

    TextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "custom",
                tint = primary
            )

        },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = primary,
            unfocusedLabelColor = primary,

            cursorColor = primary,

            focusedContainerColor = primary.copy(alpha = 0.2f),
            unfocusedContainerColor = primary.copy(alpha = 0.2f),

            focusedIndicatorColor = primary,
            unfocusedIndicatorColor = primary.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun NumberTextField(inputValue: String, label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = inputValue,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        label = { Text(text = label) },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = primary,
            unfocusedLabelColor = primary,

            cursorColor = primary,

            focusedContainerColor = primary.copy(alpha = 0.2f),
            unfocusedContainerColor = primary.copy(alpha = 0.2f),

            focusedIndicatorColor = primary,
            unfocusedIndicatorColor = primary.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun TimeInput(
    inputValue: String,
    optionSelected: String,
    options: Array<String>,
    onValueChange: (String) -> Unit,
    onMultiToggleSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        NumberTextField(
            inputValue = inputValue,
            label = "Time",
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f))
        MultiToggleButton(
            options = options,
            optionSelected = optionSelected,
            onSelect = onMultiToggleSelect,
            modifier = Modifier.weight(1f)
        )
    }
}

