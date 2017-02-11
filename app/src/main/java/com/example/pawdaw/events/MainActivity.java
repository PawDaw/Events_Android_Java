package com.example.pawdaw.events;

import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {


//    Context help variable to update List Adapter
    private static Context mContext;

    public static Context getContext() {

        return mContext;
    }

    public void setContext(Context mContext) {

        this.mContext = mContext;
    }

    List_Fragment listFra;
    DescriptionFragment descriptionFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data from JSON URL and pass to Event Array LIST when getEvent() array list is empty
        if(Service.getInstance().getEvents().isEmpty()){
            new JSON_AsyncTask().execute();
        }

        setContentView(R.layout.activity_main);

        listFra = new List_Fragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.side_panel, listFra);
        ft.commit();


    }

    // addToBackStack() so your previous state will be added to the backstack allowing you to go back with the back button.
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    //  save to JSON on STOP Application
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("............ ON STOP  ........... ");

    }




// ----------------- CLASS    JSON Async TASK -------------------

    class JSON_AsyncTask extends AsyncTask<String, String, JSONObject> {

        //URL to get JSON Array
        private  String url = "http://events.makeable.dk/api/getEvents";

        private ProgressDialog pDialog;
        private long i=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ... from JSON URL");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            // Class JSONParser connecting to the internet
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;

        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray events = json.getJSONArray("events");
//                JSONObject object = events.getJSONObject(0);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int  i=0; i< events.length(); i++) {


                // Storing  JSON item in a Variable
                String title_ENG = events.getJSONObject(i).getString("title_english");
                String sub_ENG = events.getJSONObject(i).getString("subtitle_english");
                String description = events.getJSONObject(i).getString("description_english");
                String URL = events.getJSONObject(i).getString("url");
                String imageURL = events.getJSONObject(i).getString("picture_name");


                // go in to Array with START and END time
                JSONArray ArrayDATE = events.getJSONObject(i).getJSONArray("datelist");
                JSONObject o = ArrayDATE.getJSONObject(0);
                String ST = o.getString("start");

                JSONObject o1 = ArrayDATE.getJSONObject(0);
                String ET = o.getString("end");


                // Convert UNIX time normal Format
                Calendar startdate = Calendar.getInstance();
                startdate.setTimeInMillis(Long.parseLong(ST) * 1000);
//                String StartDATE = startdate.get(Calendar.DAY_OF_MONTH) + "." + startdate.get(Calendar.MONTH) + "." + startdate.get(Calendar.YEAR) + "  " + startdate.get(Calendar.HOUR_OF_DAY) + ":" + startdate.get(Calendar.MINUTE);

                Calendar enddate = Calendar.getInstance();
                enddate.setTimeInMillis(Long.parseLong(ET) * 1000);


                //Set JSON Data to the Events ArrayList
                Event event = new Event(i++, title_ENG, sub_ENG, startdate.getTime().toString(), enddate.getTime().toString(), description, URL, imageURL);
                Service.getInstance().events.add(event);

                // update the List adapter
                listFra.updateList();
            }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    // -----------   BAR ACTIONS  ,  METHODS ---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //    ---------   DataBase Bar ICON  --------------
        Intent basket = new Intent(MainActivity.this, Database_Activity.class);
        startActivity(basket);

        System.err.println("DataBase ICON Pressed !!!!!!!!!!  ");

        return super.onOptionsItemSelected(item);
    }


}
