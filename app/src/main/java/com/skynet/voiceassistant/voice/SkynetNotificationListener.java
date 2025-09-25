package com.skynet.voiceassistant.voice;

import android.service.notification.NotificationListenerService;import android.os.PowerManager;
import android.media.AudioManager;
import android.app.KeyguardManager;
import android.os.Build;
import android.speech.tts.Voice;
import java.util.Locale;
import java.util.Set;

import android.service.notification.StatusBarNotification;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Reads notifications from user-selected packages and speaks them.
 * Selected packages stored in SharedPreferences under key "notif_apps" as CSV.
 */
public class SkynetNotificationListener extends NotificationListenerService {
    private static final String TAG = "SkynetNotifListener";
    private TextToSpeech tts;
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences("skynet_prefs", Context.MODE_PRIVATE);
        try {
            tts = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) { try { tts.setLanguage(new Locale("fa","IR")); } catch(Exception e){} tts.setPitch(0.95f); tts.setSpeechRate(0.95f); try { Set<Voice> voices = tts.getVoices(); for (Voice v: voices) { if (v.getName().toLowerCase().contains("fa") || v.getLocale()!=null && v.getLocale().getLanguage().equals("fa")) { tts.setVoice(v); break; } } } catch (Exception e) {} 
                    try { tts.setLanguage(new Locale("fa","IR")); } catch(Exception e){}
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "TTS init error", e);
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) { /* updated logic applied */
        try {
            String pkg = sbn.getPackageName();
            CharSequence title = sbn.getNotification().extras.getCharSequence("android.title");
            CharSequence text = sbn.getNotification().extras.getCharSequence("android.text");
            Log.i(TAG, "Notif from: " + pkg + " title: " + title + " text: " + text);

            String appsCsv = prefs.getString("notif_apps", "");
            if (appsCsv == null || appsCsv.isEmpty()) {
                return;
            }
            Set<String> selected = new HashSet<>(Arrays.asList(appsCsv.split(",")));
            if (selected.contains(pkg)) {
                String toSpeak = (title!=null?title.toString():"") + "؛ " + (text!=null?text.toString():"");
                speak(toSpeak);
            }
        } catch (Exception e) {
            Log.e(TAG, "onNotificationPosted error", e);
        }
    }

    private void speak(String text) {
        try {
            if (tts!=null) {
                tts.speak(text, TextToSpeech.QUEUE_ADD, null, "skynet_notif");
            }
        } catch (Exception e) {
            Log.e(TAG, "speak error", e);
        }
    }

    @Override
    public void onDestroy() {
        try { if (tts!=null) tts.shutdown(); } catch (Exception e) {}
        super.onDestroy();
    }

    private boolean isSpam(String msg) {
        if (msg == null) return false;
        msg = msg.toLowerCase();
        String[] spamWords = {
            "تبلیغ", "آفر", "تخفیف", "خرید", "فروش", "وام", "قرعه‌کشی",
            "جایزه", "بورس", "شارژ", "کد هدیه", "تبلیغات",
            "promotion", "sale", "discount", "offer", "ads",
            "spam", "win", "lottery", "free", "prize", "bonus", "gift"
        };
        for (String w : spamWords) {
            if (msg.contains(w)) return true;
        }
        return false;
    }
    
}