package com.act.voicecommand;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.act.voicecommand.Activities.MainActivity;

public class Splash extends AppCompatActivity {
    public Boolean isBack = false;
    ImageView begoosh;
    private static final int PERMISSIONS_REQUEST_ALL_PERMISSIONS = 1;
    ImageView act;
    boolean isPermissionForAllGranted;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        begoosh = findViewById(R.id.begoosh_iv);
        act = findViewById(R.id.act_iv);
        if (needPermissions(this)) {
            requestPermissions();
        } else {
            new CountDownTimer(250, 10) {

                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    isBack = true;
//                AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
//                anim.setDuration(800);
//                begoosh.setVisibility(View.VISIBLE);
//                AlphaAnimation anim2 = new AlphaAnimation(0.0f, 1.0f);
//                anim2.setDuration(800);
//                begoosh.startAnimation(anim2);
                }
            }.start();
        }
        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ALL_PERMISSIONS){
            isPermissionForAllGranted = false;
            if (grantResults.length > 0 && permissions.length==grantResults.length) {
                for (int i = 0; i < permissions.length; i++){
                    isPermissionForAllGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
                requestPermissions();
            } else {
                isPermissionForAllGranted=true;

            }
            if(isPermissionForAllGranted){
                // Do your work here
                new CountDownTimer(250, 10) {

                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        isBack = true;
//                AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
//                anim.setDuration(800);
//                begoosh.setVisibility(View.VISIBLE);
//                AlphaAnimation anim2 = new AlphaAnimation(0.0f, 1.0f);
//                anim2.setDuration(800);
//                begoosh.startAnimation(anim2);
                    }
                }.start();
            }

        }
    }

    static public boolean needPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED
                    && activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECORD_AUDIO};
        requestPermissions(permissions, PERMISSIONS_REQUEST_ALL_PERMISSIONS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isBack)
            finish();
    }

}

