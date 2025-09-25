
package com.skynet.voiceassistant.voice;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import java.util.Locale;
import java.util.Set;

/**
 * Helpers to tune TTS for more natural output
 */
public class TTSSettings {
    public static void improveTTS(TextToSpeech tts) {
        try {
            tts.setSpeechRate(0.95f);
            tts.setPitch(1.05f);
            try { tts.setLanguage(new Locale("fa","IR")); } catch(Exception e){}
            try {
                Set<Voice> voices = tts.getVoices();
                if (voices != null) {
                    for (Voice v : voices) {
                        if (!v.isNetworkConnectionRequired() && v.getLocale().getLanguage().startsWith("fa")) {
                            tts.setVoice(v);
                            break;
                        }
                    }
                }
            } catch (Exception ex) { Log.w("TTSSettings","voice pick failed",ex); }
        } catch (Exception e) { Log.e("TTSSettings","error",e); }
    }
}
