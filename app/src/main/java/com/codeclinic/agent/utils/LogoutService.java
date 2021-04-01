package com.codeclinic.agent.utils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class LogoutService extends Service {
    public static CountDownTimer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        long seconds = Long.parseLong(sessionManager.getTokenDetails().get(SessionManager.ExpiresIn));
        seconds = seconds * 1000;
        //long finalSeconds = seconds;
        timer = new CountDownTimer(seconds, 1000) {
            public void onTick(long millisUntilFinished) {
                //Some code
                Log.v("sessionStarted", "timer seconds : " + millisUntilFinished);
            }

            public void onFinish() {
                Log.v("sessionCleared", "Call Logout by Service");
                sessionManager.logoutUser();
                stopSelf();
            }
        };
        timer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
