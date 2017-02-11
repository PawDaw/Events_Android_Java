package com.example.pawdaw.events;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;



/**
 * Created by pawdaw on 23/05/16.
 */
public class DescriptionFragment extends Fragment {

    private static int index;

    public static int getIndex() {

        return  DescriptionFragment.index;
    }

    public static void setIndex(int index) {

        DescriptionFragment.index = index;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return  null;

        View view = inflater.inflate(R.layout.description_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);



        TextView title = (TextView) getActivity().findViewById(R.id.title);
        TextView time = (TextView) getActivity().findViewById(R.id.Start_End);
        TextView time2 = (TextView) getActivity().findViewById(R.id.End);
        TextView subTitle =  (TextView) getActivity().findViewById(R.id.subTitle);
        TextView url = (TextView) getActivity().findViewById(R.id.url);
        TextView description = (TextView) getActivity().findViewById(R.id.description);
        ImageView image = (ImageView) getActivity().findViewById(R.id.imageView);

        ImageButton button = (ImageButton) getActivity().findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the Event in Database
                int ID = Service.getInstance().getEvents().get(index).getId();
                String title = Service.getInstance().getEvents().get(index).getTitle();
                String subTitle = Service.getInstance().getEvents().get(index).getSubTitle();
                String startTime= Service.getInstance().getEvents().get(index).getStartTime();
                String endTime= Service.getInstance().getEvents().get(index).getEndTime();
                String description= Service.getInstance().getEvents().get(index).getDescription();
                String URL= Service.getInstance().getEvents().get(index).getURL();
                String imageURL= Service.getInstance().getEvents().get(index).getImageURL();

                DAO dao = new DAO(getActivity());


                Event event = new Event(ID,title,subTitle,startTime,endTime,description,URL,imageURL);
                dao.addEvent(event);

                Toast.makeText(v.getContext(), "Event stored in Database :) ", Toast.LENGTH_LONG).show();

            }
        });



        if (title != null || time != null || subTitle != null || description != null || url != null || image != null ){

            title.setText(Service.getInstance().getEvents().get(index).getTitle());
            time.setText(Service.getInstance().getEvents().get(index).getStartTime());
            time2.setText(Service.getInstance().getEvents().get(index).getEndTime());
            subTitle.setText(Service.getInstance().getEvents().get(index).getSubTitle());
            description.setText(Service.getInstance().getEvents().get(index).getDescription());
            url.setText(Service.getInstance().getEvents().get(index).getURL());

            // Picasso, powerful image downloading and caching library
            Picasso.with(getActivity()).load(Service.getInstance().getEvents().get(index).getImageURL()).into(image);

            System.out.println("............ url ............ " +Service.getInstance().getEvents().get(index).getImageURL());



        }

    }



}


