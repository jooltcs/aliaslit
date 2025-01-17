package com.joolt.aliaslit;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static final String PREF_NAME = "user_settings";
    private static final String KEY_TIME = "time";

    private SharedPreferences sharedPreferences;

    public SettingsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveTime(int time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TIME, time);
        editor.apply();
    }

    public int getTime() {
        return sharedPreferences.getInt(KEY_TIME, 30);
    }
}
