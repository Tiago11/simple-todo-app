package com.codepath.tiago.simpletodo;

import android.app.Application;
import android.content.res.Configuration;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by tiago on 8/16/17.
 */

public class MyApplication extends Application {

    /*
     * Called when the application is starting, before any other application objects have been created.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Instantiate DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    /*
     * Called by the system when the device configuration changes while your component is running.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /*
     * This is called when the overall system is running low on memory,
     * and would like actively running processes to tighten their belts.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
