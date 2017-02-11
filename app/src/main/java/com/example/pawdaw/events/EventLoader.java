package com.example.pawdaw.events;


import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by pawdaw on 24/05/16.
 */
public class EventLoader extends CursorLoader {

    public EventLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Cursor loadInBackground() {


        SQLiteDatabase db = new DataBaseHelper(getContext()).getWritableDatabase();

        return db.query(DataBaseHelper.TABLE_NAME,getProjection(),getSelection(),getSelectionArgs(),null,null,getSortOrder());

    }
}
