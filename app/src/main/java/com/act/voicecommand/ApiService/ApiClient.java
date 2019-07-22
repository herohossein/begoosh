package com.act.voicecommand.ApiService;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getWeatherClient(){
        if (retrofit == null){
            String BASE_URL = "http://api.openweathermap.org/data/2.5/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getPrayerTimeClient(){
        if (retrofit == null){
            String BASE_URL = "http://api.aladhan.com/v1/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getTranlation(){
        if (retrofit == null){
            String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
