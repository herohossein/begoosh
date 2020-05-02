package com.act.voicecommand.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.act.voicecommand.BaseActivity;
import com.act.voicecommand.R;

public class HelpUsActivity extends BaseActivity {

	LinearLayout lvBtn;
	TextView tv;
	
@SuppressLint("SetTextI18n")
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_help_us);
	
	init();
	
	tv.setText("«به\u200Cگوش» یک برنامه کاملا رایگانه!\n" +
			           "اما برای توسعه دادن این برنامه ما به یاری و کمک\u200Cهای شما نیاز داریم.\n" +
			           "شما می\u200Cتونید برای کمک به تیم ما، از طریق درگاه زیر از ما حمایت کنید.");
	
	lvBtn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Uri uri = Uri.parse("https://zarinp.al/@actteam"); // missing 'http://' will cause crashed
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	});
	
}

private void init() {
	lvBtn=findViewById(R.id.lv_btn);
	tv=findViewById(R.id.help_us_tv1);
}
}
