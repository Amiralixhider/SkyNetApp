package com.skynet.voiceassistant.settings;

import android.content.SharedPreferences;
import android.content.Context;

public class DefaultAppManager {
    public static void setDefaultApp(String appType, String packageName, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("skynet_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString(appType, packageName).apply();
    }
    public static String getDefaultApp(String appType, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("skynet_prefs", Context.MODE_PRIVATE);
        return prefs.getString(appType, null);
    }
}
