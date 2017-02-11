package com.example.pawdaw.events;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by pawdaw on 24/05/16.
 */
public class List_Database_Fragment extends ListFragment  {

    // True or False depending on if we are in horizontal or duel pane mode
    private boolean landscape;
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_2 = 2;



    // Currently selected item in the ListView
    int  mCurCheckPosition;

    public int getmCurCheckPosition() {
        return mCurCheckPosition;
    }

    public void setmCurCheckPosition(int mCurCheckPosition) {
        this.mCurCheckPosition = mCurCheckPosition;
    }



    private static row_adapter_Database adapter;

    public row_adapter_Database getAdapter() {
        return adapter;
    }

    public void setAdapter(row_adapter_Database adapter) {
        this.adapter = adapter;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // If the screen is rotated onSaveInstanceState() below will store the
        // hero most recently selected. Get the value attached to curChoice and store it in mCurCheckPosition
        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        // set list of Events to adapter and pass to row layout
        adapter = new row_adapter_Database(getActivity(),null);
        setListAdapter(adapter);


        // CHOICE_MODE_SINGLE allows one item in the ListView to be selected at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Make the currently selected item highlighted
        getListView().setItemChecked(mCurCheckPosition, true);


        View detailFrame = getActivity().findViewById(R.id.description_panel);

        landscape = detailFrame !=null;
        if (landscape) {
            getFragmentManager().popBackStack();
            showDetails(mCurCheckPosition);
        }


        // ContextMenu functionality  ---- IMPORTANT
        registerForContextMenu(getListView());
        setHasOptionsMenu(true);


    }

    //     SHOW method
    void showDetails(int position) {

        DescriptionFragment details = new DescriptionFragment();
        details.setIndex(position);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        // Check if we are in horizontal mode and if yes show the ListView and
        // the Travel data
        if (landscape) {
            // Replace the content of the container
            // ft.replace(R.id.flContainer, new_fragment);
            ft.replace(R.id.description_panel, details);
        } else {
            // Replace whatever is in the fragment_container view with this fragment,   transaction.replace(R.id.fragment_container, newFragment);
            // and add the transaction to the back stack
            ft.replace(R.id.side_panel, details);

            // addToBackStack(null) so your previous state will be added to the backstack allowing you to go back with the back button.
            // MainActivity method onBackPressed() need to be implemented
            ft.addToBackStack("null");

        }

        // Commit the changes
        ft.commit();
    }

    // We save the last item selected in the list here and attach it to the key curChoice
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Select the action");
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //  DELETE
        if(item.getTitle().equals("Delete")) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int itemPosition = info.position;

            //  get instance from DAO
            DAO dao = new DAO(getActivity());

            // new instance of DAO , deleteRow method called
            Event event = Service.getInstance().getEvents().get(itemPosition);
            dao.deleteRow(itemPosition);

            // call method onContentChanged from row_Adapter_Database to refresh content after deleting item
            adapter.onContentChanged();


            Toast.makeText(getActivity(),"Deleted on position: " + itemPosition,Toast.LENGTH_LONG).show();

        }

        return true;
    }

}

