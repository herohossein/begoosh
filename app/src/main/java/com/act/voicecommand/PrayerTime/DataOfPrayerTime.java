package com.act.voicecommand.PrayerTime;

import com.google.gson.annotations.SerializedName;

public class DataOfPrayerTime {
    @SerializedName("timings")
    private Timing timing;

    @SerializedName("date")
    private Date date;

    public Timing getTiming() {
        return timing;
    }

    public Date getDate() {
        return date;
    }
}
