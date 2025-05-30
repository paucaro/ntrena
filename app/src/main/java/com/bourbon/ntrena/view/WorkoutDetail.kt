package com.bourbon.ntrena.view

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bourbon.ntrena.WorkoutDetailViewModel
import com.bourbon.ntrena.models.Exercise
import com.bourbon.ntrena.models.Workout
import com.bourbon.ntrena.ui.theme.primary40
import com.bourbon.ntrena.ui.theme.primaryColor
import com.bourbon.ntrena.ui.theme.secondaryColor
import com.bourbon.ntrena.util.primary
import kotlinx.coroutines.delay

@Composable
fun WorkoutDetail(
    workoutDetailViewModel: WorkoutDetailViewModel,
    workout: Workout,
    series: Int,
    context: Context = LocalContext.current
) {
    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                workout.name?.let { Text(it, fontSize = 48.sp, fontWeight = FontWeight.Bold, color = primary) }
                HorizontalDivider()
                workout.exercises?.let {
                    WorkoutTimer(it, series,
                        modifier = Modifier.fillMaxSize(),
                        speak = { },
                        onExerciseFinished = { workoutDetailViewModel.exerciseFinished(context) },
                        onFinished = { workoutDetailViewModel.serieFinished(context) }
                    )
                }

            }
        }
    )
}

@Composable
fun WorkoutTimer(
    exercises: List<Exercise>,
    series: Int,
    modifier: Modifier = Modifier,
    speak: (String) -> Unit = {},
    onExerciseFinished: () -> Unit = {},
    onFinished: () -> Unit = {}
) {
    var currentSeries by remember { mutableIntStateOf(1) }
    var currentStep by remember { mutableIntStateOf(-1) } // -1: cuenta inicial
    var timeLeft by remember { mutableIntStateOf(5) }
    var isRest by remember { mutableStateOf(false) }

    LaunchedEffect(currentStep, isRest, currentSeries) {
        if (currentStep == -1) {
            println(">>> Comenzando serie $currentSeries de $series")
            while (timeLeft > 0) {
                println("Inicio en: $timeLeft")
                delay(1000)
                timeLeft--
            }
            currentStep = 0
            timeLeft = exercises.getOrNull(currentStep)?.duration ?: 0
        } else if (currentStep < exercises.size) {
            val exercise = exercises[currentStep]
            if (!isRest) {
                println("Ejercicio: ${exercise.name}")
                while (timeLeft > 0) {
                    println("${exercise.name}: $timeLeft")
                    delay(1000)
                    timeLeft--
                }
                onExerciseFinished()
                if (currentStep < exercises.lastIndex) {
                    isRest = true
                    timeLeft = 5
                } else {
                    currentStep++
                }
            } else {
                println("Descanso:")
//                speak("Siguiente ejercicio: ${exercises[currentStep ++].name}")
                while (timeLeft > 0) {
                    println("Descanso: $timeLeft")
                    delay(1000)
                    timeLeft--
                }
                currentStep++
                timeLeft = exercises.getOrNull(currentStep)?.duration ?: 0
                isRest = false
            }
        } else {
            if (currentSeries < series) {
                currentSeries++
                currentStep = -1
                timeLeft = 5
                isRest = false
            } else {
                println("✅ ¡Entrenamiento completo!")
                onFinished()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            "Series: $currentSeries / $series",
            fontSize = 40.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(20.dp),
            color = secondaryColor
        )
        when {
            currentStep == -1 -> "Iniciando"
            currentStep >= exercises.size -> "¡Finalizado!"
            isRest -> "Descanso"
            else -> exercises[currentStep].name
        }?.let {
            Text(
                text = it,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        when {
            currentStep == -1 -> 5
            currentStep >= exercises.size -> 0
            isRest -> 5
            else -> exercises[currentStep].duration
        }?.let {
            CountdownCircle(
                timeLeft = timeLeft,
                totalTime = it
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            (if (currentStep + 1 >= exercises.size) "Fin" else exercises[currentStep + 1].name)?.let {
                Text(
                    it,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(0.5f),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun CountdownCircle(
    timeLeft: Int,
    totalTime: Int, // en segundos
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = if (totalTime == 0) 0.toFloat() else timeLeft / totalTime.toFloat(),
        animationSpec = tween(1000),
        label = "CountdownProgress"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(200.dp)
            .padding(16.dp)
    ) {
        // Fondo del círculo
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = primary40)
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Texto con los segundos restantes
        Text(
            text = "$timeLeft",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
