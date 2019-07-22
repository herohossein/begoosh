package com.act.voicecommand;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    public Boolean isBack = false;
    ImageView begoosh;
    ImageView act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        begoosh = findViewById(R.id.begoosh_iv);
        act = findViewById(R.id.act_iv);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (isBack)
            finish();
    }
}
