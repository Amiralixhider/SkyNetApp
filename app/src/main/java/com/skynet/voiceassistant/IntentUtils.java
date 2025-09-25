package com.skynet.voiceassistant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class IntentUtils {
    public static void sendWhatsAppMessage(Context ctx, String phoneWithCountry, String message) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + Uri.encode(phoneWithCountry) + "&text=" + Uri.encode(message);
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
        } catch (Exception e) { Log.e("IntentUtils","whatsapp",e); }
    }
    public static void sendTelegramMessage(Context ctx, String username, String message) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://t.me/" + Uri.encode(username) + "?text=" + Uri.encode(message);
            i.setPackage("org.telegram.messenger");
            i.setData(Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
        } catch (Exception e) { Log.e("IntentUtils","telegram",e); }
    }
}
