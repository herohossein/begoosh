package com.act.voicecommand.Weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
//    @SerializedName("coord")
//    private Coord coord;
    @SerializedName("weather")
    private List<Weather> weather;
//    @SerializedName("base")
//    private String base;
    @SerializedName("main")
    private Main main;
//    @SerializedName("visibility")
//    private int visibility;
    @SerializedName("wind")
    private Wind wind;
//    @SerializedName("cloud")
//    private Cloud cloud;
//    @SerializedName("dt")
//    private long dt;
//    @SerializedName("sys")
//    private Sys sys;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("cod")
    private int cod;

    public List<Weather> getWeather() {
        return weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }
}
