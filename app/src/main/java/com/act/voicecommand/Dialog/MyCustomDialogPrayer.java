package com.act.voicecommand.Dialog;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.act.voicecommand.ApiService.ApiClient;
import com.act.voicecommand.ApiService.ApiInterface;
import com.act.voicecommand.BaseActivity;
import com.act.voicecommand.Calendar.ChangeDate;
import com.act.voicecommand.CommandAction;
import com.act.voicecommand.MyDatabase;
import com.act.voicecommand.PrayerTime.DataOfPrayerTime;
import com.act.voicecommand.PrayerTime.PrayerTimeResponse;
import com.act.voicecommand.R;
import com.act.voicecommand.RecAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    TextView dateTv;
    ProgressBar loading;
    LinearLayoutManager linearLayoutManager;
    int[] pageNumber = {0};
    int temp;
    Calendar calendar;
    double longitude = 0;
    double latitude = 0;
    private FusedLocationProviderClient client;
    int month;
    boolean state;
    boolean cityState;

    private static final String TAG = "MyCustomDialogPrayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_dialog_prayer);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        init();
        Intent intent = getIntent();
        cityName = intent.getStringExtra(CommandAction.CITY_NAME);
        cityState = intent.getBooleanExtra(CommandAction.CITY_STATE, true);

        String title = "یه لحظه صبر کن!! ";
        icons = new ArrayList<>();
        prayTime = new ArrayList<>();
        times = new ArrayList<>();
        texts = new ArrayList<>();
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        pageNumber[0] = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        getPrayerTime(cityName);

        titleTv.setText(title);
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
                Log.d(TAG, "onClick: " + pageNumber[0]);
                texts.clear();
                pageNumber[0]--;
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date d = null;
                if (pageNumber[0] < 0) {
                    rightButton.setClickable(false);
                    month--;
                    showPrayTime(cityName, latitude, longitude);
                    try {
                        d = dateFormat.parse(times.get(temp).getDate().getGregorian().getDate().replace('-', '/'));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateFormat.applyPattern("yyyy/MM/dd");
                    dateTv.setText(ChangeDate.changeMiladiToFarsi(dateFormat.format(d)));
                    texts.add(times.get(temp).getTiming().getFajr().substring(0, 6));
                    icons.add("morning");
                    texts.add(times.get(temp).getTiming().getSunrise().substring(0, 6));
                    icons.add("sunrise");
                    texts.add(times.get(temp).getTiming().getDhuhr().substring(0, 6));
                    icons.add("noon");
                    texts.add(times.get(temp).getTiming().getSunset().substring(0, 6));
                    icons.add("sunset");
                    texts.add(times.get(temp).getTiming().getMaghrib().substring(0, 6));
                    icons.add("evening");
                    texts.add(times.get(temp).getTiming().getMidnight().substring(0, 6));
                    icons.add("midnight");
                } else {
                    try {
                        d = dateFormat.parse(times.get(pageNumber[0]).getDate().getGregorian().getDate().replace('-', '/'));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateFormat.applyPattern("yyyy/MM/dd");
                    dateTv.setText(ChangeDate.changeMiladiToFarsi(dateFormat.format(d)));
                    texts.add(times.get(pageNumber[0]).getTiming().getFajr().substring(0, 6));
                    icons.add("morning");
                    texts.add(times.get(pageNumber[0]).getTiming().getSunrise().substring(0, 6));
                    icons.add("sunrise");
                    texts.add(times.get(pageNumber[0]).getTiming().getDhuhr().substring(0, 6));
                    icons.add("noon");
                    texts.add(times.get(pageNumber[0]).getTiming().getSunset().substring(0, 6));
                    icons.add("sunset");
                    texts.add(times.get(pageNumber[0]).getTiming().getMaghrib().substring(0, 6));
                    icons.add("evening");
                    texts.add(times.get(pageNumber[0]).getTiming().getMidnight().substring(0, 6));
                    icons.add("midnight");
                }
                adapter = new RecAdapter(MyCustomDialogPrayer.this, Byte.valueOf("0"), texts, icons, null);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + pageNumber[0]);
                texts.clear();

                pageNumber[0]++;

                if (pageNumber[0] > times.size() - 1) {
                    pageNumber[0] = 0;
                    month++;
                    showPrayTime(cityName, latitude, longitude);
                }


                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date d = null;
                try {
                    d = dateFormat.parse(times.get(pageNumber[0]).getDate().getGregorian().getDate().replace('-', '/'));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateFormat.applyPattern("yyyy/MM/dd");
                dateTv.setText(ChangeDate.changeMiladiToFarsi(dateFormat.format(d)));
                texts.add(times.get(pageNumber[0]).getTiming().getFajr().substring(0, 6));
                icons.add("morning");
                texts.add(times.get(pageNumber[0]).getTiming().getSunrise().substring(0, 6));
                icons.add("sunrise");
                texts.add(times.get(pageNumber[0]).getTiming().getDhuhr().substring(0, 6));
                icons.add("noon");
                texts.add(times.get(pageNumber[0]).getTiming().getSunset().substring(0, 6));
                icons.add("sunset");
                texts.add(times.get(pageNumber[0]).getTiming().getMaghrib().substring(0, 6));
                icons.add("evening");
                texts.add(times.get(pageNumber[0]).getTiming().getMidnight().substring(0, 6));
                icons.add("midnight");

                adapter = new RecAdapter(MyCustomDialogPrayer.this, Byte.valueOf("0"), texts, icons, null);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void init() {
        closeButton = findViewById(R.id.close2);
        rightButton = findViewById(R.id.right2);
        leftButton = findViewById(R.id.left2);
        titleTv = findViewById(R.id.title_tv2);
        recyclerView = findViewById(R.id.rec2);
        dateTv = findViewById(R.id.date_tv);
        loading = findViewById(R.id.loading);
    }

    private void getPrayerTime(String cityName) {

        MyDatabase myDatabase = new MyDatabase(this);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        prayTime = new ArrayList<>();

        cityName = " " + cityName;
        cityName = cityName.replace("ی", "ي");
        final String finalCityName = cityName;
        Cursor cursor = db.rawQuery("Select * from cities where cityName = ?", new String[]{cityName});

        if (cursor.moveToFirst()) {
            latitude = cursor.getDouble(3);
            longitude = cursor.getDouble(4);
            showPrayTime(finalCityName, latitude, longitude);
            state = true;
            cursor.close();
        } else if (cityState) {
            if (ActivityCompat.checkSelfPermission(
                    MyCustomDialogPrayer.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyCustomDialogPrayer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            client.getLastLocation().addOnSuccessListener(MyCustomDialogPrayer.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        showPrayTime(" ", latitude, longitude);
                    } else
                        TastyToast.makeText(MyCustomDialogPrayer.this, "متاسفانه نمیتونم شهرت رو پیدا کنم لطفا همراه با دستور اسم شهرت هم بگو", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }
            });
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
                                    if (location != null) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                        showPrayTime(" ", latitude, longitude);
                                    } else
                                        TastyToast.makeText(MyCustomDialogPrayer.this, "متاسفانه نمیتونم شهرت رو پیدا کنم لطفا همراه با دستور اسم شهرت هم بگو", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
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

    private void showPrayTime(final String name, double lat, double lon) {
        titleTv.setText("یه لحظه صبر کن !!");
        dateTv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getPrayerTimeClient().create(ApiInterface.class);

        final Calendar calendar = Calendar.getInstance();
        temp = times.size() - 1;
        Call<PrayerTimeResponse> call = apiService.getPrayerTime(lat, lon,
                month, calendar.get(Calendar.YEAR), 1, 7);
        call.enqueue(new Callback<PrayerTimeResponse>() {
            private static final String TAG = "myCustomPrayer";

            @Override
            public void onResponse(Call<PrayerTimeResponse> call, Response<PrayerTimeResponse> response) {
                loading.setVisibility(View.GONE);
                dateTv.setVisibility(View.VISIBLE);
                rightButton.setClickable(true);
                prayTime.clear();
                assert response.body() != null;
                String title;
                if (state) {
                    title = "اوقات شرعی " + name;
                } else
                    title = "اوقات شرعی";
                titleTv.setText(title);

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date d = null;

                if (pageNumber[0] < 0) {
                    pageNumber[0] = response.body().getData().size() - 1;
                }
                try {
                    d = dateFormat.parse(response.body().getData().get(pageNumber[0]).getDate().getGregorian().getDate().replace('-', '/'));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateFormat.applyPattern("yyyy/MM/dd");

                dateTv.setText(ChangeDate.changeMiladiToFarsi(dateFormat.format(d)));
                prayTime.add(response.body().getData().get(pageNumber[0]).getTiming().getFajr().substring(0, 6));
                icons.add("morning");
                prayTime.add(response.body().getData().get(pageNumber[0]).getTiming().getSunrise().substring(0, 6));
                icons.add("sunrise");
                prayTime.add(response.body().getData().get(pageNumber[0]).getTiming().getDhuhr().substring(0, 6));
                icons.add("noon");
                prayTime.add(response.body().getData().get(pageNumber[0]).getTiming().getSunset().substring(0, 6));
                icons.add("sunset");
                prayTime.add(response.body().getData().get(pageNumber[0]).getTiming().getMaghrib().substring(0, 6));
                icons.add("evening");
                prayTime.add(response.body().getData().get(pageNumber[0]).getTiming().getMidnight().substring(0, 6));
                icons.add("midnight");

                times.clear();
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
