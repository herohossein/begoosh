package com.act.voicecommand;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static boolean isFirstTime(Context mContext)
    {
        final String MY_PREFS_NAME = mContext.getApplicationContext().getPackageName() + "myPrefs";
        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        final String key = mContext.getPackageName() + "isFirstTime";
        boolean isFirstTime = prefs.getBoolean(key, true);
        if (isFirstTime)
        {
            editor.putBoolean(key, false);
            editor.apply();
            return true;
        }
        else
            return false;
    }
}