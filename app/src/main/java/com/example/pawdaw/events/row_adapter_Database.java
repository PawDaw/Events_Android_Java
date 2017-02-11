package com.example.pawdaw.events;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pawdaw on 25/05/16.
 */
public class row_adapter_Database extends CursorAdapter {



    public row_adapter_Database(Context context, Cursor c) {

        super(context, c);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.row,parent,false);
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Find fields to populate in inflated template
        TextView title = (TextView) view.findViewById(R.id.title_row);
        TextView startTime = (TextView) view.findViewById(R.id.start_row);
        TextView endTime = (TextView) view.findViewById(R.id.end_row);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        // Extract properties from cursor
        String titleCursor = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String startTimeCursor = cursor.getString(cursor.getColumnIndexOrThrow("startTime"));
        String endTimeCursor = cursor.getString(cursor.getColumnIndexOrThrow("endTime"));

        checkBox.setVisibility(View.GONE);

        // Populate fields with extracted properties
        title.setText(titleCursor);
        startTime.setText(startTimeCursor);
        endTime.setText(endTimeCursor);

        // current position
        final int position = cursor.getPosition();

    }

    // Refresh content of the list after Content Changed
    @Override
    protected void onContentChanged() {
        // TODO Auto-generated method stub
        super.onContentChanged();
        notifyDataSetChanged();
    }


}
