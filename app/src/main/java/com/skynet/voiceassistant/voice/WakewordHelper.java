
package com.skynet.voiceassistant.voice;

import android.content.Context;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.content.Intent;
import java.util.ArrayList;

public class WakewordHelper {
    private SpeechRecognizer recognizer;
    private Context ctx;
    private static final String TAG = "WakewordHelper";
    public interface Callback { void onWakeDetected(); }
    private Callback callback;

    public WakewordHelper(Context ctx, Callback cb) {
        this.ctx = ctx; this.callback = cb;
        try {
            recognizer = SpeechRecognizer.createSpeechRecognizer(ctx);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override public void onReadyForSpeech(Bundle params) {}
                @Override public void onBeginningOfSpeech() {}
                @Override public void onRmsChanged(float rmsdB) {}
                @Override public void onBufferReceived(byte[] buffer) {}
                @Override public void onEndOfSpeech() {}
                @Override public void onError(int error) { restartListening(); }
                @Override public void onPartialResults(Bundle partialResults) {}
                @Override public void onEvent(int eventType, Bundle params) {}
                @Override public void onResults(Bundle results) {
                    try {
                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        if (matches != null) {
                            for (String s: matches) {
                                String lower = s.toLowerCase();
                                if (lower.contains("hey skynet") || lower.contains("هی اسکای نت") || lower.contains("مستر سکای نت")) {
                                    if (callback!=null) callback.onWakeDetected();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) { Log.e(TAG, "onResults", e); }
                    restartListening();
                }
            });
        } catch (Exception e) { Log.e(TAG, "init", e); }
    }

    public void startListening() {
        try {
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
            recognizer.startListening(i);
        } catch (Exception e) { Log.e(TAG, "start", e); }
    }

    public void stopListening() { try { recognizer.stopListening(); recognizer.cancel(); } catch(Exception e){} }

    private void restartListening() {
        try { Thread.sleep(200); startListening(); } catch(Exception e){}
    }
}
