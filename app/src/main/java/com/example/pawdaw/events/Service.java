package com.example.pawdaw.events;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pawdaw on 23/05/16.
 */
public class Service {

    private boolean InitUsed = false;
    ArrayList<Event> events = new ArrayList<Event>();

    // ---------------Singleton pattern--------------------------
    /**
     * Singleton pattern - It is used when you want to eliminate the option of instantiating more than one object !!! We can use in STORAGE, SERVICE
     */

    private static Service instance = new Service();

    Service() {
        //
    }

    public static Service getInstance() {

        return instance;
    }

//    ------------------------------------


    public ArrayList<Event> getEvents() {

        return this.getInstance().events;
    }



}
