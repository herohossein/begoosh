package com.act.voicecommand.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.act.voicecommand.BaseActivity;
import com.act.voicecommand.CommandAction;
import com.act.voicecommand.Dialog.VoiceDialog;
import com.act.voicecommand.MyDatabase;
import com.act.voicecommand.R;

import java.util.List;


public class MainActivity extends BaseActivity {

    public String appLink = "لینک اپ برای استراک گذاری";
    private static final String TAG = "mainActivity";
    public int mainCounter = 100;
    public ImageView voiceBtn;
    public ImageView tutorialIv;
    public ImageView shareIv;
    public ImageView aboutIv;
    public ImageView supportIv;
    public ImageView typeIv;
    public ImageView promoteIv;
    SharedPreferences prefs = null;
    SpeechRecognizer recognizer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setFinishOnTouchOutside(false);
        init();

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        tutorialIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(i);

            }
        });
        
        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                Intent i;
                i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, appLink);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, "یکی از گزینه های زیر را انتخاب کنید:"));

            }
        });

        aboutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);

            }
        });

        supportIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(i);
            }
        });
        typeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Speech2TextActivity.class);
                startActivity(i);
            }
        });
        promoteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

			Intent i = new Intent(getApplicationContext(), HelpUsActivity.class);
			startActivity(i);
//			TastyToast.makeText(getApplicationContext(), "Hello World !", TastyToast.LENGTH_LONG, TastyToast.INFO);
            }
        });

    }


    public void init() {
        voiceBtn = findViewById(R.id.voicebtn);
        tutorialIv = findViewById(R.id.tutorial_iv);
        shareIv = findViewById(R.id.share_iv);
        aboutIv = findViewById(R.id.about_iv);
        supportIv = findViewById(R.id.support_iv);
        typeIv = findViewById(R.id.type_iv);
        promoteIv = findViewById(R.id.promote_iv);
        prefs = getSharedPreferences("com.act.voicecommand", MODE_PRIVATE);
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (mainCounter <= 100 && mainCounter > 0) {
            //            Log.d("tag", "onClick: OK");
            //        startVoiceRecognitionActivity();
            mainCounter--;
            if (mainCounter == 97) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("یه پیشنهاد جالب!");
                alertDialog.setMessage("می\u200Cدونستی که می\u200Cتونی همین الان ویجت «به\u200Cگوش» رو روی صفحه اصلی گوشیت بذاری تا سریع و آسون بهش دسترسی داشته باشی؟");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, " باشه!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), VoiceDialog.class);
                                startActivity(i);
                            }
                        });
                alertDialog.show();
            } else {
                Intent i = new Intent(this, VoiceDialog.class);
                startActivity(i);
            }
//        } else if (mainCounter <= 0) {
//            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//            alertDialog.setTitle("متاسفم!");
//            alertDialog.setMessage("برای ادامه استفاده از به گوش، باید اونو ارتقا بدی!");
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ارتقا بده!",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, " نه نمیخوام!",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
        }

    }

//    private void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(LocationServices.API).build();
//        googleApiClient.connect();
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(10000 / 2);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i(TAG, "All location settings are satisfied.");
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
//
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
//                        break;
//                }
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            getStateContact();

//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                getStateContact();
//            }

            MyDatabase myDatabase;
            SQLiteDatabase sqLiteDatabase;
            myDatabase = new MyDatabase(getApplicationContext());
            sqLiteDatabase = myDatabase.getReadableDatabase();
            String temp;
            String temp2;
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
            for (int i = 0; i < pkgAppsList.size(); i++) {
                temp = pkgAppsList.get(i).loadLabel(getPackageManager()).toString();
                temp2 = pkgAppsList.get(i).activityInfo.packageName;

                sqLiteDatabase.execSQL("insert into apps (name, package) values ('" + temp + "', '" + temp2 + "')");
            }
            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }


    private void getStateContact() {
        int temp = 0, temp2 = 0;
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            temp++;
            Log.d(TAG, "getStateContact: " + cursor.getString(0));
            if (cursor.getString(0) != null) {
                if (CommandAction.textPersian(cursor.getString(0).charAt(0))) {
                    temp2++;
                }
            }
        }
        cursor.close();
        if (temp == temp2) {
            prefs.edit().putBoolean("contact_state", true).apply();
        }
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.e(TAG, "onRequestPermissionsResult: " + requestCode);
//        switch (requestCode){
//            case CommandAction.REQUEST_CAMERA:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    Log.e(TAG, "onRequestPermissionsResult: ");
//                break;
//        }
//    }
}