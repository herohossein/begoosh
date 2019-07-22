package com.act.voicecommand;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyCustomDialog extends BaseActivity {

    Byte condition;
    String title;
    String textMessage;
    List<String> texts;
    List<String> icons;
    RecAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_dialog);

        Intent intent = getIntent();

        title = intent.getStringExtra(CommandAction.TITLE);
        texts = intent.getStringArrayListExtra(CommandAction.TEXT);
        textMessage = intent.getStringExtra(CommandAction.TEXT_MESSAGE);
        icons = intent.getStringArrayListExtra(CommandAction.ICONS);
        condition = intent.getByteExtra(CommandAction.CONDITION, Byte.valueOf("0"));

        recyclerView = findViewById(R.id.rec);
        adapter = new RecAdapter(this, condition, texts, icons, textMessage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.getStackFromEnd();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView closeButton = findViewById(R.id.close);
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText(title);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
