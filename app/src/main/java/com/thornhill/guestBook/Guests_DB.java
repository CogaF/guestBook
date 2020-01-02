package com.thornhill.guestBook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Guests_DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "guest.db";
    private static final String TABLE_NAME = "Guest_List";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "SURNAME";
    private static final String COL_4 = "VISITEE";
    private static final String COL_5 = "SIGNATURE";
    private static final String COL_6 = "ENTRY_TIME";
    private static final String COL_7 = "EXIT_TIME";
    private static final String COL_8 = "STATUS";

    public Guests_DB(Context cntx){
        super(cntx,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME
                    + " (" + COL_1 +  " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " " + COL_2 + " TEXT,"
                    + " " + COL_3 + " TEXT,"
                    + " " + COL_4 + " TEXT,"
                    + " " + COL_5 + " TEXT,"
                    + " " + COL_6 + " DATE,"
                    + " " + COL_7 + " BLOB,"
                    + " " + COL_8 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //for the moment i will not need to upgrade the database
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean outgoingGuest(String name, String surname){
        boolean myReturn = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_7, getDateTime());
        cv.put(COL_8, "0");
        int returnInt = db.update(TABLE_NAME, cv,
                COL_2 + " " + name + " and " + COL_3 + " " + surname,
                null);
        if(returnInt == 0 ){
            myReturn = false;
        }

        return myReturn;

    }
    public boolean insertGuest(String name, String surname, String visitee, byte[] signature){
        boolean insertResult = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, surname);
        cv.put(COL_4, visitee);
        cv.put(COL_5, signature);
        cv.put(COL_6, getDateTime());
        cv.put(COL_8, "1");
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            insertResult = false;
        }
        return insertResult;
    }


    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
