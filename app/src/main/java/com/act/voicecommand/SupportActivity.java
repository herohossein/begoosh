package com.act.voicecommand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SupportActivity extends BaseActivity {

    Button send;
    EditText msg;
    EditText subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        send = findViewById(R.id.send_btn);
        subject = findViewById(R.id.sub_et);
        msg = findViewById(R.id.msg_et);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent ;
                intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@act-dev.ir"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString()+" (به گوش)");
                intent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            }
        });

    }
}
