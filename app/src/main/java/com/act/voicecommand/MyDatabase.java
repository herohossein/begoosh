package com.act.voicecommand;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

private static final String DATABASE_NAME = "dictionary.db";
public static int DATABASE_VERSION = 1;

public MyDatabase (Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
setForcedUpgrade();
}
public void setDB_version(int vr){
	DATABASE_VERSION=vr;
}
public int getDB_version(){
	return DATABASE_VERSION;
}
}
