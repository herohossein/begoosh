package com.act.voicecommand.Dialog;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.act.voicecommand.BaseActivity;
import com.act.voicecommand.CommandAction;
import com.act.voicecommand.MyDatabase;
import com.act.voicecommand.R;
import com.act.voicecommand.RecAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCustomDialog extends BaseActivity {

    Byte condition;
    String title;
    String textMessage;
    List<String> texts;
    List<String> icons;
    List<Drawable> drawableList;
    RecAdapter adapter;
    RecyclerView recyclerView;
//change
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_dialog);

        drawableList = new ArrayList<>();

        Intent intent = getIntent();
        title = intent.getStringExtra(CommandAction.TITLE);
        texts = intent.getStringArrayListExtra(CommandAction.TEXT);
        textMessage = intent.getStringExtra(CommandAction.TEXT_MESSAGE);
        icons = intent.getStringArrayListExtra(CommandAction.ICONS);
        condition = intent.getByteExtra(CommandAction.CONDITION, Byte.valueOf("0"));

        recyclerView = findViewById(R.id.rec);
        if (icons == null) {
            for (int i = 0; i < 3; i++) {
            MyDatabase myDatabase = new MyDatabase(this);
            SQLiteDatabase db = myDatabase.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from apps where name = ?", new String[]{texts.get(i)});
            cursor.moveToFirst();
                try {
                    Drawable icon = getPackageManager().getApplicationIcon(cursor.getString(1));
                    drawableList.add(icon);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                cursor.close();
        }
            adapter = new RecAdapter(this, condition, texts, drawableList);
        } else
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
