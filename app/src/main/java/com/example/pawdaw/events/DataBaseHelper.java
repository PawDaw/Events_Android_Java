package com.example.pawdaw.events;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pawdaw on 23/05/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    public static final String TABLE_NAME = "events";

    // Collums
    public static final String ID = "_id";  	// for cursor adapter the table (or the cursor) must include a column named "_id"
    public static final String TITL = "title";
    public static final String SUBTITLE = "subTitle";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";
    public static final String IMAGEURL = "imageURL";


    public static final String[] ALL_COLUMNS = new String[] {ID, TITL, SUBTITLE,STARTTIME,ENDTIME,DESCRIPTION,URL,IMAGEURL};
    public static final String[] LIST_COLUMNS = new String[] {TITL, SUBTITLE,STARTTIME,ENDTIME,DESCRIPTION,URL,IMAGEURL};
    public static final String ID_EQUAL = ID + "=?";



    // constructor
    DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // use QUERY to create a table   = column + attribute
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID + " integer primary key autoincrement, " + TITL + " text not null, " +SUBTITLE + " text not null, " +STARTTIME+ " text not null, " +ENDTIME+ " text not null, " +DESCRIPTION+ " text not null, " +URL+ " text not null, " +IMAGEURL+ " text not null);";



    @Override
    public void onCreate(SQLiteDatabase db) {

        //  pass the variable CREATE_TABLE and create table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //	ON Update DROP and create again
        db.execSQL("drop table if it exists " + TABLE_NAME);
        onCreate(db);

    }


}
