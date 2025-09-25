package com.skynet.voiceassistant

import android.content.Context
import android.speech.tts.TextToSpeech
import org.json.JSONObject
import java.io.InputStream

object TtsDefaultsLoader {
    fun applyDefaults(context: Context, tts: TextToSpeech, langCode: String, mode: String) {
        try {
            val isStream: InputStream = context.resources.openRawResource(
                context.resources.getIdentifier("tts_defaults", "raw", context.packageName)
            )
            val json = JSONObject(isStream.bufferedReader().use{it.readText()})
            val langObj = json.optJSONObject(langCode) ?: json.optJSONObject("en")
            val modeObj = langObj.optJSONObject(mode) ?: langObj.optJSONObject("master")
            val pitch = modeObj.optDouble("pitch", 1.0).toFloat()
            val rate = modeObj.optDouble("rate", 1.0).toFloat()
            tts.setPitch(pitch)
            tts.setSpeechRate(rate)
        } catch (e: Exception) {
            tts.setPitch(1.0f)
            tts.setSpeechRate(1.0f)
        }
    }
}
