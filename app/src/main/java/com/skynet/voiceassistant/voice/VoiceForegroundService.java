
package com.skynet.voiceassistant.voice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Locale;
import android.os.Handler;
import android.os.Looper;

/**
 * Foreground service enhanced: integrates WakewordHelper (SpeechRecognizer) for better wake detection,
 * uses TTSSettings for improved voice, and provides offline command parsing.
 */
public class VoiceForegroundService extends Service implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private static final String TAG = "VoiceFGService";
    private WakewordHelper wakeHelper;
    private Handler handler;
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        prefs = getSharedPreferences("skynet_prefs", Context.MODE_PRIVATE);
        tts = new TextToSpeech(this, this);
        TTSSettings.improveTTS(tts);
        // Start wakeword listener using SpeechRecognizer (best-effort on-device method)
        try {
            wakeHelper = new WakewordHelper(this, () -> handler.post(() -> {
                try { tts.speak("بله مستر؟", TextToSpeech.QUEUE_ADD, null, "wake"); } catch(Exception e){}
                // Here you could start a recognition session to capture full command
            }));
            wakeHelper.startListening();
        } catch (Exception e) { Log.e(TAG, "wake init", e); }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try { if (wakeHelper!=null) wakeHelper.stopListening(); } catch(Exception e){}
        if (tts!=null) tts.shutdown();
        super.onDestroy();
    }

    @Override public IBinder onBind(Intent intent) { return null; }
    @Override public void onInit(int status) { /* ready */ }

    // Basic offline command parser (delegates to previously implemented logic if present)
    public void processCommand(String cmd) {
        // TODO: integrate richer parsing or send to TextAnalytics for online handling
        if (cmd==null) return;
        cmd = cmd.toLowerCase();
        // simple phrases handled here
        if (cmd.contains("وای") && cmd.contains("فای")) {
            toggleWifi(true); speak("وای فای روشن شد"); return;
        }
        // fallback
        speak("دستور نامشخص بود");
    }

    private void toggleWifi(boolean on) {
        try {
            android.net.wifi.WifiManager wm = (android.net.wifi.WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wm!=null) wm.setWifiEnabled(on);
        } catch (Exception e) { Log.e(TAG, "toggleWifi", e); }
    }
}
