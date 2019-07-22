package com.act.voicecommand;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TutorialActivity extends BaseActivity {

    LinearLayout alarm;
    LinearLayout reminder;
    LinearLayout dic;
    LinearLayout setting;
    LinearLayout calc;
    LinearLayout apps;
    LinearLayout weather;
    LinearLayout prayer;
    LinearLayout widget;
    LinearLayout inl1;
    LinearLayout inl2;
    LinearLayout inl3;
    LinearLayout inl4;
    LinearLayout inl5;
    LinearLayout inl6;
    LinearLayout inl7;
    LinearLayout inl8;
    LinearLayout inl9;
    LinearLayout inl10;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv8;
    TextView tv7;
    TextView tv9;
    TextView tv10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        init();

        prayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("اوقات شرعی «شهر مورد نظر»؟");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("اوقات شرعی اهواز؟");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("اذان «شهر مورد نظر» کیه؟");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("اذان تبریز کیه؟");
                inl5.setVisibility(View.VISIBLE);
                tv5.setText("اذان به افق «شهر مورد نظر»؟");
                inl6.setVisibility(View.VISIBLE);
                tv6.setText("اذان به افق ساری؟");
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("هوای «شهر مورد نظر» ؟");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("هوای تهران؟");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("هوای «شهر مورد نظر» چطوره؟");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("هوای مشهد چطوره؟");
                inl5.setVisibility(View.VISIBLE);
                tv5.setText("روبیکا رو باز کن");
                inl6.setVisibility(View.VISIBLE);
                tv6.setText("اجرا کن اسنپ رو");
            }
        });

        apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("«برنامه مورد نظر» رو باز کن");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("باز کن «برنامه مورد نظر» رو");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("«برنامه مورد نظر» رو اجرا کن");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("اجرا کن «برنامه مورد نظر» رو");
                inl5.setVisibility(View.VISIBLE);
                tv5.setText("روبیکا رو باز کن");
                inl6.setVisibility(View.VISIBLE);
                tv6.setText("اجرا کن اسنپ رو");
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("حساب کن «عبارت ریاضی مورد نظر»");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("حساب کن دو ضربدر پنجاه و سه");
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("وای\u200Cفای رو روشن کن");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("وای\u200Cفای رو خاموش کن");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("حالت بی\u200Cصدا");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("قطع صدا");
                inl5.setVisibility(View.VISIBLE);
                tv5.setText("حالت لرزش");
                inl6.setVisibility(View.VISIBLE);
                tv6.setText("حالت ویبره");
                inl7.setVisibility(View.VISIBLE);
                tv7.setText("حالت باصدا");
                inl8.setVisibility(View.VISIBLE);
                tv8.setText("حالت صدادار");
                inl9.setVisibility(View.VISIBLE);
                tv9.setText("بلوتوث رو روشن کن");
                inl10.setVisibility(View.VISIBLE);
                tv10.setText("بلوتوث رو خاموش کن");
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("ساعت «ساعت مورد نظر» کوک کن");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("ساعت یازده و نیم کوک کن");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("کوک کن ساعت «ساعت مورد نظر»");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("کوک کن ساعت یک");
                inl5.setVisibility(View.VISIBLE);
                tv5.setText("ساعت «ساعت مورد نظر» بیدارم کن");
                inl6.setVisibility(View.VISIBLE);
                tv6.setText("ساعت دو بیدارم کن");
                inl7.setVisibility(View.VISIBLE);
                tv7.setText("بیدارم کن ساعت «ساعت مورد نظر»");
                inl8.setVisibility(View.VISIBLE);
                tv8.setText("بیدارم کن ساعت سه و ربع");
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("«روز مورد نظر» یادم بیار «کار مورد نظر»");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("دوشنبه یادم بیار برم دکتر");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("«روز مورد نظر» یادم بیار که «کار مورد نظر»");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("سه\u200Cشنبه یادم بیار که قسطم رو بدم");
                inl5.setVisibility(View.VISIBLE);
                tv5.setText("یادآوری امروز");
            }
        });

        dic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invisible();

                inl1.setVisibility(View.VISIBLE);
                tv1.setText("انگلیسی «کلمه مورد نظر» ؟");
                inl2.setVisibility(View.VISIBLE);
                tv2.setText("انگلیسی سلام؟");
                inl3.setVisibility(View.VISIBLE);
                tv3.setText("«کلمه مورد نظر» به انگلیسی چی میشه؟");
                inl4.setVisibility(View.VISIBLE);
                tv4.setText("سگ به انگلیسی چی میشه؟");
            }
        });

        widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(TutorialActivity.this).create();
                alertDialog.setTitle("یه پیشنهاد جالب!");
                alertDialog.setMessage("می\u200Cدونستی که می\u200Cتونی همین الان ویجت «به\u200Cگوش» رو روی صفحه اصلی گوشیت بذاری تا سریع و آسون بهش دسترسی داشته باشی؟");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, " باشه!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    private void init() {
        alarm = findViewById(R.id.alarm);
        reminder = findViewById(R.id.reminder);
        dic = findViewById(R.id.dictionary);
        setting = findViewById(R.id.setting);
        calc = findViewById(R.id.calculator);
        weather = findViewById(R.id.weather);
        apps = findViewById(R.id.apps);
        prayer = findViewById(R.id.prayer);
        widget = findViewById(R.id.widget);
        inl1 = findViewById(R.id.inl1);
        inl2 = findViewById(R.id.inl2);
        inl3 = findViewById(R.id.inl3);
        inl4 = findViewById(R.id.inl4);
        inl5 = findViewById(R.id.inl5);
        inl6 = findViewById(R.id.inl6);
        inl7 = findViewById(R.id.inl7);
        inl8 = findViewById(R.id.inl8);
        inl9 = findViewById(R.id.inl9);
        inl10 = findViewById(R.id.inl10);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
        tv10 = findViewById(R.id.tv10);
    }

    private void invisible() {
        inl1.setVisibility(View.INVISIBLE);
        inl2.setVisibility(View.INVISIBLE);
        inl3.setVisibility(View.INVISIBLE);
        inl4.setVisibility(View.INVISIBLE);
        inl5.setVisibility(View.INVISIBLE);
        inl6.setVisibility(View.INVISIBLE);
        inl7.setVisibility(View.INVISIBLE);
        inl8.setVisibility(View.INVISIBLE);
        inl9.setVisibility(View.INVISIBLE);
        inl10.setVisibility(View.INVISIBLE);
    }
}
