package com.bourbon.ntrena.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bourbon.ntrena.R
import com.bourbon.ntrena.util.primary

@Composable
fun Workout() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
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

    }
}
