package com.act.voicecommand.ApiService;

import com.act.voicecommand.PrayerTime.PrayerTimeResponse;
import com.act.voicecommand.Weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
//    @GET("weather")
//    Call<WeatherResponse> getCityWeather(@Query("q") String cityName, @Query("apikey") String apiKey);

    @GET("weather")
    Call<WeatherResponse> getCoordinateWeather(@Query("lat") double lat, @Query("lon") double lon,
                                               @Query("apikey") String apiKey);

    @GET("calendar")
    Call<PrayerTimeResponse> getPrayerTime(@Query("latitude") double lat, @Query("longitude") double lon,
                                           @Query("month") int month, @Query("year") int year, @Query("midnightMode") int mode, @Query("method") int method);
}
