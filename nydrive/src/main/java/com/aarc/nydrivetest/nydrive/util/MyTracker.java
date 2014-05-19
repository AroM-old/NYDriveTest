package com.aarc.nydrivetest.nydrive.util;

import android.app.Application;

import com.aarc.nydrivetest.nydrive.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by Algenis on 5/19/2014.
 */
public class MyTracker extends Application {
    //Property ID
    private static final String PROPERTY_ID = "UA-51122423-1";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName{
        APP_TRACKER, // use only in this app.
        GLOBAL_TRACKER,// use for all apps in the company.
        ECOMMERCE_TRACKER,//Ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public MyTracker(){
        super();
    }

    public synchronized Tracker getTracker(TrackerName trackerId){
        if (!mTrackers.containsKey(trackerId)){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    :(trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                        :analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId,t);
        }
        return mTrackers.get(trackerId);
    }
}
