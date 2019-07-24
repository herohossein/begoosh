package com.act.voicecommand;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rm.rmswitch.RMSwitch;

public class Speech2Text extends BaseActivity {
    RMSwitch mSwitch;
    TextView fa;
    TextView en;
    LinearLayout copy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_2_text);

        init();

        fa.setTextColor(Color.parseColor("#FFFCC956"));

        mSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked){
                    fa.setTextColor(Color.parseColor("#FFFCC956"));
                    en.setTextColor(Color.parseColor("#FF9164C9"));
                }else {
                    fa.setTextColor(Color.parseColor("#FF9164C9"));
                    en.setTextColor(Color.parseColor("#FFFCC956"));
                }
            }
        });
    }

    private void init() {
        mSwitch = findViewById(R.id.your_id);
        fa = findViewById(R.id.fa_tv);
        en = findViewById(R.id.en_tv);
        copy = findViewById(R.id.copy);
    }
}
