package com.example.pawdaw.events;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by pawdaw on 23/05/16.
 */
public class List_Fragment extends ListFragment {

    // True or False depending on if we are in horizontal or duel pane mode
    private boolean landscape;
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_2 = 2;


    // Currently selected item in the ListView
    int  mCurCheckPosition;
    rowAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // If the screen is rotated onSaveInstanceState() below will store the
        // hero most recently selected. Get the value attached to curChoice and store it in mCurCheckPosition
        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

//      set list of Events to adapter and pass to row layout
        adapter = new rowAdapter(getActivity(), R.layout.row, Service.getInstance().getEvents());
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

    public void updateList(){

        adapter.refreshViewList();
    }



}
