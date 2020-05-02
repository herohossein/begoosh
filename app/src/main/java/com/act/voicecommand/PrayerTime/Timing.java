package com.act.voicecommand.PrayerTime;

import com.google.gson.annotations.SerializedName;

public class Timing {
    @SerializedName("Fajr")
    private String fajr;
    @SerializedName("Sunrise")
    private String sunrise;
    @SerializedName("Dhuhr")
    private String dhuhr;
    @SerializedName("Asr")
    private String asr;
    @SerializedName("Sunset")
    private String sunset;
    @SerializedName("Maghrib")
    private String maghrib;
    @SerializedName("Isha")
    private String isha;
    @SerializedName("Imsak")
    private String imsak;
    @SerializedName("Midnight")
    private String midnight;

    public String getFajr() {
        return fajr;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getDhuhr() {
        return dhuhr;
    }

    public String getAsr() {
        return asr;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public String getImsak() {
        return imsak;
    }

    public String getMidnight() {
        return midnight;
    }
}
