package com.act.voicecommand;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    private static final String TAG = "TEST";
    public List<String> texts;
    public List<String> icons;
    public List<Drawable> drawableList;
    public Byte condition;

    String textMessage;
    Context mContext;

    public RecAdapter(Context context, Byte condition, List<String> texts, List<String> icons, String textMessage) {
        mContext = context;
        this.texts = texts;
        this.icons = icons;
        this.condition = condition;
        this.textMessage = textMessage;
    }

 public RecAdapter(Context context, Byte condition, List<String> texts, List<Drawable> drawables) {
        mContext = context;
        this.texts = texts;
        this.condition = condition;
        drawableList = drawables;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.custom_rec, parent, false);
        return new MyViewHolder(v);
    }
//change
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setText(texts.get(position));
        if (icons == null){
            holder.iv.setImageDrawable(drawableList.get(position));
            holder.iv.setImageDrawable(drawableList.get(position));
            holder.iv.setImageDrawable(drawableList.get(position));
//            Toast.makeText(mContext, "icons is null", Toast.LENGTH_SHORT).show();
//            holder.iv.set
        } else {
            switch (icons.get(position)) {
                case "temp":
                    holder.iv.setBackgroundResource(R.drawable.temp);
                    break;
                case "max":
                    holder.iv.setBackgroundResource(R.drawable.hightemp);
                    break;
                case "min":
                    holder.iv.setBackgroundResource(R.drawable.lowtemp);
                    break;
                case "wind":
                    holder.iv.setBackgroundResource(R.drawable.windspeed);
                    break;
                case "cloudy":
                    holder.iv.setBackgroundResource(R.drawable.cloudy);
                    break;
                case "contact":
                    holder.iv.setBackgroundResource(R.drawable.contact);
                    break;
                case "left":
                    holder.iv.setBackgroundResource(R.drawable.left);
                    break;
                case "right":
                    holder.iv.setBackgroundResource(R.drawable.right);
                    break;
                case "sms":
                    holder.iv.setBackgroundResource(R.drawable.sms);
                    break;
                case "evening":
                    holder.iv.setBackgroundResource(R.drawable.evening);
                    break;
                case "midnight":
                    holder.iv.setBackgroundResource(R.drawable.midnight);
                    break;
                case "noon":
                    holder.iv.setBackgroundResource(R.drawable.noon);
                    break;
                case "morning":
                    holder.iv.setBackgroundResource(R.drawable.morning);
                    break;
                case "sunset":
                    holder.iv.setBackgroundResource(R.drawable.sunset);
                    break;
                case "sunrise":
                    holder.iv.setBackgroundResource(R.drawable.sunrise);
                    break;
                case "translate":
                    holder.iv.setBackgroundResource(R.drawable.translate);
                    break;
                case "calculator":
                    holder.iv.setBackgroundResource(R.drawable.calculator);
                    break;
                default:
                    break;
            }
        }
//change
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (condition == 1) {
                    //This is item for CALL
                    String contactName = holder.tv.getText().toString();

                    call(getPhoneNumber(contactName));
                    ((BaseActivity) mContext).finish();
                } else if (condition == 2) {
                    //This is item for SMS
                    byte count = 0;
                    String text = null;
                    String contactName = holder.tv.getText().toString();
                    for (int i = 0; i < contactName.length(); i++) {
                        if (contactName.charAt(i) == ' ') {
                            count++;
                        }
                    }
                    if (count == 1) {
                        String[] separated = textMessage.split(" ", 3);
                        text = separated[2];
                    } else {
                        String[] separated = textMessage.split(" ", 2);
                        text = separated[1];
                    }
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", getPhoneNumber(contactName));
                    smsIntent.putExtra("sms_body", text);
                    mContext.startActivity(smsIntent);
                    ((BaseActivity) mContext).finish();
                } else if (condition == 3) {
                    String appName = holder.tv.getText().toString();
                    MyDatabase myDatabase = new MyDatabase(mContext);
                    SQLiteDatabase db = myDatabase.getReadableDatabase();
                    Cursor cursor = db.rawQuery("Select * from apps where name = ?", new String[]{appName});
                    cursor.moveToFirst();
                    Intent launchIntent = mContext.getPackageManager()
                            .getLaunchIntentForPackage(cursor.getString(1));
                    if (launchIntent != null) {
                        mContext.startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                    cursor.close();
                }
            }
        });
    }

    // return string contain phone number
    private String getPhoneNumber(String name) {
        //test
        if (name == null)
            return null;

        final ContentResolver cr = mContext.getContentResolver();
        String phoneNumber = null;
        String[] projection = new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER};
        final Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, projection,
                ContactsContract.Contacts.DISPLAY_NAME + " = ?", new String[]{name}, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.d(TAG, "getPhoneNumber: " + id);

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                if (pCur.moveToFirst()) {
                    assert pCur != null;
                    phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "getPhoneNumber: " + phoneNumber);
                } else {
                    Toast.makeText(mContext, "شماره پیدا نشد", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(mContext, "شماره پیدا نشد", Toast.LENGTH_LONG).show();
            }
        }
        return phoneNumber;
    }

    private void call(String phone) {
        Log.d(TAG, "call : " + phone);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ImageView iv;
        public LinearLayout layout;
        public LinearLayout item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            iv = itemView.findViewById(R.id.iv);
            layout = itemView.findViewById(R.id.layout);
            item = itemView.findViewById(R.id.item);
        }
    }
}

