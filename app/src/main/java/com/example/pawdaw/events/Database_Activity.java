package com.example.pawdaw.events;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by pawdaw on 24/05/16.
 */
public class Database_Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    //    Context help variable to update List Adapter
    private static Context mContext;

    public static Context getContext() {

        return mContext;
    }

    public void setContext(Context mContext) {

        this.mContext = mContext;
    }

    List_Database_Fragment list_database_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Init LOADER !!!!!!!!!!!  Important
        getLoaderManager().initLoader(0, null, this);

        list_database_fragment = new List_Database_Fragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.side_panel, list_database_fragment);
        ft.commit();



    }

    //   Address click LINK to google Website from description fragment
    public void OnCLickURL(View view) {
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


//    -----------  Loaders methods , LoaderManager.LoaderCallbacks<Cursor>

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new EventLoader(this,null,null,null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        list_database_fragment.getAdapter().swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        list_database_fragment.getAdapter().swapCursor(null);
    }

    // -----------   BAR ACTIONS  ,  METHODS ---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drop_database_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //    ---------  Favourite Acticity,  Bar ICON DROP  --------------


        int id = item.getItemId();

        if(id == R.id.drop){


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Database_Activity.this);

            // set title
            alertDialogBuilder.setTitle("Drop Database ?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to drop DATABASE!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            // if this button is clicked, drop DATABASE
                            DAO dao = new DAO(Database_Activity.this);
                            dao.clearDatabase();

                            // if this button is clicked, close current activity
                            Intent main = new Intent(Database_Activity.this, MainActivity.class);
                            startActivity(main);

                            System.err.println("Drop ICON Pressed !!!!!!!!!!  ");
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }



        return super.onOptionsItemSelected(item);
    }

}
