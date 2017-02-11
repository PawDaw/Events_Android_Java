package com.example.pawdaw.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by pawdaw on 24/05/16.
 */
public class DAO {

    SQLiteDatabase db;

    public DAO(Context context) {

        // 1. get reference to writable DB
        db = new DataBaseHelper(context).getWritableDatabase();
    }

    public long addEvent(Event event){

        ContentValues values = new ContentValues();

        //    put ( column name ; value)
//        values.put(DataBaseHelper.ID, event.getId());
        values.put(DataBaseHelper.TITL, event.getTitle());
        values.put(DataBaseHelper.SUBTITLE, event.getSubTitle());
        values.put(DataBaseHelper.STARTTIME, event.getStartTime());
        values.put(DataBaseHelper.ENDTIME, event.getEndTime());
        values.put(DataBaseHelper.DESCRIPTION, event.getDescription());
        values.put(DataBaseHelper.URL, event.getURL());
        values.put(DataBaseHelper.IMAGEURL, event.getImageURL());

        // insert new data to database
        return db.insert(DataBaseHelper.TABLE_NAME, null, values);
    }

    public int deleteEvent_By_ID(String id){

        return db.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.ID_EQUAL, new String[] {id});
    }

    public void deleteEvent(Event event) {

        // delete
        db.delete(DataBaseHelper.TABLE_NAME, //table name
                DataBaseHelper.TITL + " = ?",  // selections column
                new String[]{String.valueOf(event.getTitle())}); //selections args to search for

        close();
        Log.d("..... Delete Event.. : ", event.toString());

    }

    public int clearDatabase(){

        return db.delete(DataBaseHelper.TABLE_NAME,null,null);
    }

    public Event getEvent(String id) {

        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME,DataBaseHelper.ALL_COLUMNS,DataBaseHelper.ID_EQUAL,new String[] {id}, null, null, null);

        // Event
        EventCursor eventCursor = new EventCursor(cursor);


        if (cursor.moveToFirst()) {
            return eventCursor.getEvent();
        }
        else
        {
            cursor.close();
            return null;
        }
    }

    public boolean exists(String searchItem) {

        String[] columns = { DataBaseHelper.TITL };
        String selection = DataBaseHelper.TITL + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    //  GET EVENTS
    public Cursor getEvents() {

        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME, DataBaseHelper.ALL_COLUMNS, null, null, null, null, null);

        EventCursor personCursor = new EventCursor(cursor);

        return personCursor;
    }

    // Delete row from list view
    public void deleteRow(int position){

        Cursor c = getEvents();
        int i = 0;

        // go through all rows from the DATABASE and count them used variable "i" and check if  i = position(from clicked on list ) -> IF TRUE -> get ID from Database and delete item by ID.
        while (c.moveToNext()){
            if(i== position){
                int id = c.getInt(c.getColumnIndex(DataBaseHelper.ID));
                deleteEvent_By_ID(String.valueOf(id));
                Log.d("...Delete event ID : ", String.valueOf(id));
            }
           i++;
        }
        c.close();
    }


    public void close() {

        db.close();
    }


}
