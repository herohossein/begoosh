package com.act.voicecommand.PrayerTime;

import com.google.gson.annotations.SerializedName;

public class Date {

    @SerializedName("readable")
    private String read;

    @SerializedName("gregorian")
    private Gregorian gregorian;

    public Gregorian getGregorian() {
        return gregorian;
    }

    public String getRead() {
        return read;
    }
}
