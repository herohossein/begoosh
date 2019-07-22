package com.act.voicecommand.PrayerTime;

import com.google.gson.annotations.SerializedName;

public class DataOfPrayerTime {
    @SerializedName("timings")
    private Timing timing;

    public Timing getTiming() {
        return timing;
    }


}
