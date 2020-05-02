package com.act.voicecommand;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;


public class SQLiteHandler extends SQLiteOpenHelper {

  private static final String TAG = "SQLiteHandler";

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "act_db";
  public static final String TABLE_CONTACTS = "contacts";

  public static final String KEY_ID = "id";
  public static final String KEY_CONTACT_NAME = "contact_name";

  private Context mContext;

  public SQLiteHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    mContext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CONTACT_NAME + " TEXT)";
    db.execSQL(CREATE_CONTACT_TABLE);

    Log.d(TAG, "Database Created");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    onCreate(db);
  }

  public void addAllContacts(){

    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();

    String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
    Cursor allContact = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
            null, null, null);
    assert allContact != null;
    while (allContact.moveToNext()) {
      values.put(KEY_ID, allContact.getInt(0));
      values.put(KEY_CONTACT_NAME, allContact.getString(1));
      db.insert(TABLE_CONTACTS, null, values);
    }
    allContact.close();
  }

  public Cursor getCursorOfContacts(){
    SQLiteDatabase db = getReadableDatabase();
    return db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_CONTACT_NAME},
            null,null, null, null, null);
  }

  public String[] getArrayOfContacts(){
    SQLiteDatabase db = getReadableDatabase();
    String[] contacts = new String[10000];

    Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_CONTACT_NAME},
            null,null, null, null, null);
    for (int i = 0; cursor.moveToNext(); i++) {
      contacts[i] = cursor.getString(1);
    }
    cursor.close();
    return contacts;
  }
}
