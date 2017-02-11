package com.example.pawdaw.events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pawdaw on 23/05/16.
 */
public class rowAdapter extends ArrayAdapter<Event> {

    Context context;
    int layoutResourceId;

    MainActivity mainActivity;

    public rowAdapter(Context context, int layoutResourceId, ArrayList<Event> events) {
        super(context, layoutResourceId, events);
        this.context = context;
        this.layoutResourceId = layoutResourceId;


    }

    // method to reload ViewList
    public void refreshViewList() {

        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View row = convertView;

        // view lookup cache stored in tag
        ViewHolder holder = null;


        // Get the data item for this position
        final Event eventNote = getItem(position);



        if ( row == null){

//          Refresh List View
            refreshViewList();

            holder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId,parent,false);

            holder.title = (TextView) row.findViewById(R.id.title_row);
            holder.startTime = (TextView) row.findViewById(R.id.start_row);
            holder.endTime = (TextView) row.findViewById(R.id.end_row);
            holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);






            //    CHECK BOX on click Listener
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    //   IF Checked = TRUE
                    if (((CheckBox) v).isChecked()) {
                        Toast.makeText(v.getContext(),"Event stored in Database :) ", Toast.LENGTH_LONG).show();

                        // save the Event in Database
                        int ID = eventNote.getId();
                        String title = eventNote.getTitle();
                        String subTitle = eventNote.getSubTitle();
                        String startTime= eventNote.getStartTime();
                        String endTime= eventNote.getEndTime();
                        String description= eventNote.getDescription();
                        String URL= eventNote.getURL();
                        String imageURL= eventNote.getImageURL();

                        DAO dao = new DAO(getContext());


                        Event event = new Event(ID,title,subTitle,startTime,endTime,description,URL,imageURL);
                        dao.addEvent(event);


                    }else{

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("Delete");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click yes to delete event from favorite")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // if this button is clicked, delete from favorite
                                        DAO dao = new DAO(getContext());
                                        dao.deleteEvent(eventNote);

                                        Toast.makeText(parent.getContext(), "Event deleted from Database", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close the dialog box and setChecked true
                                       ((CheckBox) v).setChecked(true);
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();


                    }
                }
            });


            row.setTag(holder);


        }else{


            holder = (ViewHolder) row.getTag();

        }

        // Populate the data into the template view using the data object holder.title.setText(Integer.toString(eventNote.getId()) );
        holder.title.setText(eventNote.getTitle() );
        holder.startTime.setText(eventNote.getStartTime());
        holder.endTime.setText(eventNote.getEndTime());



        //  get instance from DAO
        DAO dao = new DAO(getContext());

        // check if title exist in Database, if exist return TRUE otherwise FALSE
        if (dao.exists(eventNote.getTitle())) {
            // mark check box
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }
        // Return the completed view to render on screen
        return row;
    }


    public class ViewHolder {

        TextView title;
        TextView startTime;
        TextView endTime;
        CheckBox checkBox;


    }


}
