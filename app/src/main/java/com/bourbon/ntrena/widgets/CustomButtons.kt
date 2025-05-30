package com.bourbon.ntrena.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bourbon.ntrena.util.primary
import com.bourbon.ntrena.util.secondary


@Composable
fun AddButton(size: Dp? = null, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = secondary,
        contentColor = Color.White,
        shape = RoundedCornerShape(100),
        modifier = if (size != null) Modifier.size(size) else Modifier
    ) {
        Icon(Icons.Filled.Add, "Add button.")
    }
}

@Composable
fun ActionButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick,
        colors = ButtonDefaults.buttonColors(containerColor = primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, "label")
            Text(label)
        }
    }
}

@Composable
fun MultiToggleButton(
    options: Array<String>,
    optionSelected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(57.dp)
    ) {
        options.forEach { option ->
            Button(
                onClick = { onSelect(option) },
                modifier = Modifier.weight(1f).fillMaxHeight(),
                shape = RectangleShape,
                border = BorderStroke(1.dp, color = primary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (optionSelected == option) primary else Color.Transparent,
                    contentColor = if (optionSelected == option) Color.White else Color.Black,
                ),
            ) {
                Text(option)
            }
        }
    }
}