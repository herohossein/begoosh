package com.act.voicecommand;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.SpeechRecognizer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends BaseActivity {

    public int mainCounter = 100;

    private static final int PERMISSIONS_REQUEST_ALL_PERMISSIONS = 1;
    public ImageView voiceBtn;
    public ImageView tutorialIv;
    public ImageView aboutIv;
    public ImageView supportIv;
    public ImageView typeIv;

    SharedPreferences prefs = null;


    SpeechRecognizer recognizer;
    CommandAction commandAction;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setFinishOnTouchOutside(false);
        init();

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        if (needPermissions(this)) {
            requestPermissions();
        } else {
//            List<String> list = new ArrayList<>();
//            list.add("تماس با مامان جون");
//            commandAction = new CommandAction(this, list);
//            commandAction.callCommand();
        }

        boolean isFirstTime = Utils.isFirstTime(this);
        if (isFirstTime) {

        }


        tutorialIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        aboutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        supportIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        typeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Speech2TextActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {

            SQLiteDatabase sqLiteDatabase = null;
            ContentValues cv = null;

            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER},
                    null, null, null);
            assert cursor != null;
            while (cursor.moveToNext()) {
                if (cursor.getInt(1) > 0)
                    cv.put("name", cursor.getString(0));
                sqLiteDatabase.insert("contacts", null, cv);
            }
            cursor.close();

            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    public void init() {
        voiceBtn = findViewById(R.id.voicebtn);
        tutorialIv = findViewById(R.id.tutorial_iv);
        aboutIv = findViewById(R.id.about_iv);
        supportIv = findViewById(R.id.support_iv);
        typeIv = findViewById(R.id.type_iv);
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
//            Toast.makeText(getApplicationContext(), String.valueOf(mainCounter), Toast.LENGTH_LONG).show();

        } else if (mainCounter <= 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("متاسفم!");
            alertDialog.setMessage("برای ادامه استفاده از به گوش، باید اونو ارتقا بدی!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ارتقا بده!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, " نه نمیخوام!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    //    public void stopSpeech(View view) {
//        Log.d(TAG, "stopSpeech: ");
//        recognizer.stopListening();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
    static public boolean needPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.WRITE_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.READ_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.RECORD_AUDIO};
        requestPermissions(permissions, PERMISSIONS_REQUEST_ALL_PERMISSIONS);
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


}