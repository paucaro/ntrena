package com.bourbon.ntrena

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.bourbon.ntrena.models.Workout
import com.bourbon.ntrena.room.AppDatabase
import com.bourbon.ntrena.ui.theme.NtrenaTheme
import com.bourbon.ntrena.view.Workout
import com.bourbon.ntrena.view.WorkoutDetail
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale("es", "ES")
            }
        }

        enableEdgeToEdge()
        setContent {
            NtrenaTheme {
                val navController = rememberNavController()

                val database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-ntrena"
                ).build()
                val workoutDao = database.workoutDao()

                val workoutViewModel by viewModels<WorkoutViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return WorkoutViewModel(workoutDao) as T
                        }
                    }
                })

                val workoutDetailViewModel by viewModels<WorkoutDetailViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return WorkoutDetailViewModel(textToSpeech) as T
                        }
                    }
                })

                NavHost(
                    navController = navController,
                    startDestination = WorkoutsPage
                ) {
                    composable<WorkoutsPage> {
                        Workout(workoutViewModel, navController)
                    }

                    composable<WorkoutDetailPage> {
                        val args = it.toRoute<WorkoutDetailPage>()
                        val workout = Json.decodeFromString<Workout>(args.workoutJson)
                        WorkoutDetail(
                            workoutDetailViewModel,
                            workout = workout,
                            series = args.series
                        )
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}

@Serializable
object WorkoutsPage

@Serializable
data class WorkoutDetailPage(
    val workoutJson: String,
    val series: Int
)
