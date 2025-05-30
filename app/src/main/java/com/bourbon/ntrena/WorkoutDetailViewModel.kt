package com.bourbon.ntrena

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Locale

class WorkoutDetailViewModel(private val textToSpeech: TextToSpeech): ViewModel() {

    fun speakExercise(text: String) {
        viewModelScope.launch {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun exerciseFinished(context: Context) {
        viewModelScope.launch {
            val mediaPlayer = MediaPlayer.create(context, R.raw.catmeow)
            mediaPlayer.start()
        }
    }

    fun serieFinished(context: Context) {
        viewModelScope.launch {
            val mediaPlayer = MediaPlayer.create(context, R.raw.finish)
            mediaPlayer.start()
        }
    }
}