package com.codeclinic.agent.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.codeclinic.agent.activity.LoginActivity;

import java.util.HashMap;

/**
 * Created by Inforaam on 6/29/2017.
 */

public class SessionManager {


    public static final String AccessToken = "accessToken";
    public static final String ExpiresIn = "expiresIn";
    public static final String RefreshToken = "refreshToken";
    public static final String RefreshExpiresIn = "refreshExpiresIn";


    public static final String LOGIN_USERNAME = "login_user_name";
    public static final String USER_PASSWORD = "user_password";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    // Sharedpref file name
    private static final String PREF_NAME = "Agent";
    private static final String FIRST_TIME_PREF = "agent_first_time_launch";

    // All Shared Preferences Keys-
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_REMEMBER_ME = "IsRememberMe";
    public static SessionManager sessionManager;

    // Shared Preferences
    private final SharedPreferences pref;
    private final SharedPreferences pref1;
    private final SharedPreferences prefCredentials;

    // Editor for Shared preferences
    private final Editor editor;
    private final Editor editor1;
    private final Editor editorCredentials;

    // Context
    private final Context _context;
    // Shared pref mode
    private final int PRIVATE_MODE = 0;


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        sessionManager = this;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref1 = _context.getSharedPreferences(FIRST_TIME_PREF, PRIVATE_MODE);
        prefCredentials = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor1 = pref1.edit();
        editorCredentials = prefCredentials.edit();

    }

    public void setLoginCredentials(String userName, String userPassword) {
        editorCredentials.putString(LOGIN_USERNAME, userName);
        editorCredentials.putString(USER_PASSWORD, userPassword);
        editorCredentials.putBoolean(IS_REMEMBER_ME, true);
        editorCredentials.commit();
    }

    public HashMap<String, String> getUserCredentials() {
        HashMap<String, String> user = new HashMap<>();
        user.put(LOGIN_USERNAME, prefCredentials.getString(LOGIN_USERNAME, ""));
        user.put(USER_PASSWORD, prefCredentials.getString(USER_PASSWORD, ""));
        return user;
    }

    public void disableRememberMe() {
        editorCredentials.putBoolean(IS_REMEMBER_ME, false);
        editorCredentials.commit();
    }

    public boolean isRememberMe() {
        return prefCredentials.getBoolean(IS_REMEMBER_ME, false);
    }

    public void setUserSession(String accessToken, String expiresIn, String refreshToken, String refreshExpiresIn) {
        editor.putString(AccessToken, accessToken);
        editor.putString(ExpiresIn, expiresIn);
        editor.putString(RefreshToken, refreshToken);
        editor.putString(RefreshExpiresIn, refreshExpiresIn);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(AccessToken, pref.getString(AccessToken, null));
        user.put(ExpiresIn, pref.getString(ExpiresIn, null));
        user.put(RefreshToken, pref.getString(RefreshToken, null));
        user.put(RefreshExpiresIn, pref.getString(RefreshExpiresIn, null));

        return user;
    }

    public boolean isFirstTimeLaunch() {
        return pref1.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor1.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor1.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        //((Activity) _context).finishAffinity();
    }


}
