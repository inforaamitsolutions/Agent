package com.codeclinic.agent.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.codeclinic.agent.activity.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class RefreshTokenWorker extends Worker {
    Disposable disposable;
    private Context context;

    public RefreshTokenWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {


        try {
            long seconds = Long.parseLong(sessionManager.getUserDetails().get(SessionManager.ExpiresIn));
            seconds = seconds * 1000;
            long finalSeconds = seconds;

            disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (sessionManager.isLoggedIn()) {
                            if (aLong == finalSeconds) {
                                Log.i("loginLog", "work Complete");
                                sessionManager.logoutUser();
                                context.startActivity(
                                        new Intent(context, LoginActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        } else {
                            disposable.dispose();
                        }
                    });

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.i("loginLog", "" + e.getMessage());
        }


        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
