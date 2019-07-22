package com.act.voicecommand.PrayerTime;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PrayerTimeResponse {
    @SerializedName("data")
    private ArrayList<DataOfPrayerTime> data;

    public ArrayList<DataOfPrayerTime> getData() {
        return data;
    }
}
