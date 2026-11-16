package org.michat.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {

    private static SharedPreferences sharedPreferences;
    private static Context context;

    public static void init(Context mainActivity) {
        context = mainActivity;
        sharedPreferences = context.getSharedPreferences("michat_prefs", Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, null);
    }
}
