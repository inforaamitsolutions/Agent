package com.codeclinic.agent.utils;

import android.app.Application;
import android.util.Log;

import com.codeclinic.agent.database.LocalDatabase;
import com.codeclinic.agent.retrofit.RestClass;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RxJavaPlugins.setErrorHandler(e -> {
            if (e.getMessage() != null)
                Log.i("rxJavaError", e.getMessage());
        });

        //create session
        new SessionManager(this);

        //create Retrofit singleton instance
        RestClass.createRetrofitInstance();

        //create Local Database
        LocalDatabase.createInstance(this);

    }
}
