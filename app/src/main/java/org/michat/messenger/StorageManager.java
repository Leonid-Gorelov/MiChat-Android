package org.michat.messenger;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {

    public final String SESSION_TOKEN = "session_token";
    public final String USER_ID = "user_id";
    private SharedPreferences sharedPreferences;

    public void init(Context mainActivity) {
        sharedPreferences = mainActivity.getSharedPreferences("michat_prefs", Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }
}
