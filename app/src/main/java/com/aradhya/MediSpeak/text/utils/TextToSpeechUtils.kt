package com.aradhya.MediSpeak.text.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class TextToSpeechUtils(context: Context) : TextToSpeech.OnInitListener {

    private val textToSpeech: TextToSpeech = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
        } else {
            Log.e(TAG, "Failed to initialize TextToSpeech")
        }
    }

    fun speak(text: String) {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun shutdown() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    companion object {
        private const val TAG = "TextToSpeechUtils"
    }
}
