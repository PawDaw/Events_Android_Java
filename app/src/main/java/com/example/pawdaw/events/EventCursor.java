package com.example.pawdaw.events;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by pawdaw on 24/05/16.
 */
public class EventCursor extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     */
    public EventCursor(Cursor cursor) {

        super(cursor);
    }

    public Event getEvent(){

        int id = getInt(getColumnIndex(DataBaseHelper.ID));
        String title = getString(getColumnIndex(DataBaseHelper.TITL));
        String subTitle = getString(getColumnIndex(DataBaseHelper.SUBTITLE));
        String startTime = getString(getColumnIndex(DataBaseHelper.STARTTIME));
        String endTime = getString(getColumnIndex(DataBaseHelper.ENDTIME));
        String description = getString(getColumnIndex(DataBaseHelper.DESCRIPTION));
        String url = getString(getColumnIndex(DataBaseHelper.URL));
        String imageURL = getString(getColumnIndex(DataBaseHelper.IMAGEURL));

        return new Event(id,title,subTitle,startTime,endTime,description,url,imageURL);
    }
}
