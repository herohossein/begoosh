package com.act.voicecommand.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.act.voicecommand.BaseActivity;
import com.act.voicecommand.R;

public class AboutActivity extends BaseActivity {
    TextView tv1;
    TextView tv2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tv1 = findViewById(R.id.about_tv1);
        tv2 = findViewById(R.id.about_tv2);

//        tv.setText(R.string.about);
        tv1.setText("برنامه «به\u200Cگوش» توسط تیم نرم\u200Cافزاری ACT تولید شده و به صورت رایگان جهت استفاده شما کاربران عزیز و گرامی در دسترس قرار گرفته. ایده\u200C این برنامه از اون جایی شروع شد که هدف ما ایجاد آسون\u200Cترین راه ممکن برای استفاده از گوشی هوشمندتون بود. حالا شما می\u200Cتونید با صحبت کردن با گوشی\u200C هوشمندتون، همه کارای روزمره\u200Cتون رو انجام بدید.\n" +
                "\n" +
                "تیم نرم\u200Cافزاری ACT که از سال 1398 فعالیت خودش آغاز کرد، تو حوزه\u200Cهای طراحی و تولید اپلیکیشن اندروید، وب اپلیکیشن و وب سایت به صورت تخصصی کار خودشو داره ادامه میده.\n" +
                "\n" +
                "تیم ما با هدف توسعه برنامه\u200Cهای نرم\u200Cافزاری برای آسون کردن هر چه بیش\u200Cتر کارای روزمره، اپلیکیشن\u200Cهایی برای سیستم\u200Cعامل اندروید ایده\u200Cپردازی می\u200Cکنه و برای استفاده شما عزیزان در دسترس قرار می\u200Cده، با استفاده از این اپلیکیشن\u200Cها، لذت استفاده از به\u200Cروزترین ایده\u200Cها رو تجربه کنین.  \n");

        tv2.setText("شما می\u200Cتونین از طریق ایمیل، تلگرام و وب\u200Cسایت با ما در ارتباط باشین.\n" +
                "\n" +
                "ایمیل:   Support@act-dev.ir\n" +
                "\n" +
                "آی\u200Cدی تلگرام: @ActDevTeam\n" +
                "\n" +
                "وب\u200Cسایت: www.act-dev.ir\n");

    }
}
