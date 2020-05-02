package com.act.voicecommand;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ApplicationLoader extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/BKoodk.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
