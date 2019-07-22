package com.act.voicecommand;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.act.voicecommand.ApiService.ApiClient;
import com.act.voicecommand.ApiService.ApiInterface;
import com.act.voicecommand.PrayerTime.DataOfPrayerTime;
import com.act.voicecommand.PrayerTime.PrayerTimeResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MyCustomDialogPrayer extends BaseActivity {

    String cityName;
    List<String> texts;
    List<String> icons;
    List<String> prayTime;
    List<DataOfPrayerTime> times;
    RecAdapter adapter;
    RecyclerView recyclerView;
    ImageView closeButton;
    ImageView rightButton;
    ImageView leftButton;
    TextView titleTv;
    LinearLayoutManager linearLayoutManager;
    int pageNumber;
    Calendar calendar;
    private FusedLocationProviderClient client;


    private static final String TAG = "MyCustomDialogPrayer";
//    Byte condition = 0;


//    public MyCustomDialog(String title, List<String> texts, List<String> icons) {
//        this.title=title;
//        this.texts = texts;
//        this.icons = icons;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_dialog_prayer);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        cityName = intent.getStringExtra(CommandAction.CITY_NAME);
//        ArrayList<DataOfPrayerTime> prayTimes = new ArrayList<>((ArrayList<DataOfPrayerTime>) intent.getSerializableExtra(CommandAction.DATAOFPRAYER));
//        condition = b.getByte(CommandAction.CONDITION);
//        texts = b.getStringArrayList(CommandAction.TEXT);
//        icons = b.getStringArrayList(CommandAction.ICONS);

        String title = "یه لحظه صبر کن!! ";
        icons = new ArrayList<>();
        prayTime = new ArrayList<>();
        times = new ArrayList<>();
        texts = new ArrayList<>();
        calendar = Calendar.getInstance();
        pageNumber = calendar.get(Calendar.DAY_OF_MONTH);
        getPrayerTime(cityName, calendar.get(Calendar.DAY_OF_MONTH));

        closeButton = findViewById(R.id.close2);
        rightButton = findViewById(R.id.right2);
        leftButton = findViewById(R.id.left2);
        titleTv = findViewById(R.id.title_tv2);
        titleTv.setText(title);
        recyclerView = findViewById(R.id.rec2);

        adapter = new RecAdapter(this, Byte.valueOf("0"), prayTime, icons, null);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.getStackFromEnd();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texts.clear();
                if (pageNumber < 0) {
                    pageNumber++;
                }

                pageNumber--;

                texts.add(times.get(pageNumber).getTiming().getFajr());
                icons.add("morning");
                texts.add(times.get(pageNumber).getTiming().getSunrise());
                icons.add("sunrise");
                texts.add(times.get(pageNumber).getTiming().getDhuhr());
                icons.add("noon");
                texts.add(times.get(pageNumber).getTiming().getSunset());
                icons.add("sunset");
                texts.add(times.get(pageNumber).getTiming().getMaghrib());
                icons.add("evening");
                texts.add(times.get(pageNumber).getTiming().getMidnight());
                icons.add("midnight");

                adapter = new RecAdapter(MyCustomDialogPrayer.this, Byte.valueOf("0"), texts, icons, null);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texts.clear();
                if (pageNumber > times.size() - 2) {
                    pageNumber--;
                }

                pageNumber++;

                texts.add(times.get(pageNumber).getTiming().getFajr());
                icons.add("morning");
                texts.add(times.get(pageNumber).getTiming().getSunrise());
                icons.add("sunrise");
                texts.add(times.get(pageNumber).getTiming().getDhuhr());
                icons.add("noon");
                texts.add(times.get(pageNumber).getTiming().getSunset());
                icons.add("sunset");
                texts.add(times.get(pageNumber).getTiming().getMaghrib());
                icons.add("evening");
                texts.add(times.get(pageNumber).getTiming().getMidnight());
                icons.add("midnight");

                adapter = new RecAdapter(MyCustomDialogPrayer.this, Byte.valueOf("0"), texts, icons, null);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void getPrayerTime(String cityName, final int num) {
        final double[] longitude = {0};
        final double[] latitude = {0};

        MyDatabase myDatabase = new MyDatabase(this);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        prayTime = new ArrayList<>();

        cityName = " " + cityName;
        cityName = cityName.replace("ی", "ي");
        final String finalCityName = cityName;
        Cursor cursor = db.rawQuery("Select * from cities where cityName = ?", new String[]{cityName});

        if (cursor.moveToFirst()) {
            latitude[0] = cursor.getDouble(3);
            longitude[0] = cursor.getDouble(4);
            showPrayTime(finalCityName, num, latitude[0], longitude[0]);
            cursor.close();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("شهر مورد نظر پیدا نشد!");
            alertDialog.setMessage("میخوای اوقات شرعی جایی که هستی رو بهت نشون بدم ؟");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "اره!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (ActivityCompat.checkSelfPermission(
                                    MyCustomDialogPrayer.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyCustomDialogPrayer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            client.getLastLocation().addOnSuccessListener(MyCustomDialogPrayer.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null){
                                        latitude[0] = location.getLatitude();
                                        longitude[0] = location.getLongitude();
                                        showPrayTime(" ", num, latitude[0], longitude[0]);
                                    }
                                }
                            });
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, " نه!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            alertDialog.show();
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        }

    }

    private void showPrayTime(final String name, final int num, double lat, double lon){

        ApiInterface apiService = ApiClient.getPrayerTimeClient().create(ApiInterface.class);

        final Calendar calendar = Calendar.getInstance();

        Call<PrayerTimeResponse> call = apiService.getPrayerTime(lat, lon,
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), 1, 7);
        call.enqueue(new Callback<PrayerTimeResponse>() {
            private static final String TAG = "myCustomPrayer";

            @Override
            public void onResponse(Call<PrayerTimeResponse> call, Response<PrayerTimeResponse> response) {
                assert response.body() != null;
                String title = "اوقات شرعی " + name;
                titleTv.setText(title);

                prayTime.add(response.body().getData().get(num).getTiming().getFajr());
                icons.add("morning");
                prayTime.add(response.body().getData().get(num).getTiming().getSunrise());
                icons.add("sunrise");
                prayTime.add(response.body().getData().get(num).getTiming().getDhuhr());
                icons.add("noon");
                prayTime.add(response.body().getData().get(num).getTiming().getSunset());
                icons.add("sunset");
                prayTime.add(response.body().getData().get(num).getTiming().getMaghrib());
                icons.add("evening");
                prayTime.add(response.body().getData().get(num).getTiming().getMidnight());
                icons.add("midnight");

                times.addAll(response.body().getData());

                adapter = new RecAdapter(MyCustomDialogPrayer.this, Byte.valueOf("0"), prayTime, icons, null);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PrayerTimeResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}
