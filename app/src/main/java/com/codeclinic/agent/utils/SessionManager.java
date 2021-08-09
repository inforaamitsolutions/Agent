package com.codeclinic.agent.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {

    /*Tokens*/
    public static final String AccessToken = "accessToken";
    public static final String UName = "uName";
    public static final String ExpiresIn = "expiresIn";
    public static final String RefreshToken = "refreshToken";
    public static final String RefreshExpiresIn = "refreshExpiresIn";
    public static final String tokenSetTime = "tokenSetTime";
    public static final String tokenRenewTime = "tokenRenewTime";

    /*User Details*/
    public static final String UserID = "userId";
    public static final String UserName = "userName";
    public static final String UserEmail = "userEmail";
    public static final String FirstName = "firstName";
    public static final String LastName = "lastName";
    public static final String OtherName = "OtherName";
    public static final String PhoneNumber = "phoneNumber";


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

  /*  public void setLoginCredentials(String userName, String userPassword) {
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
    }*/

    public void disableRememberMe() {
        editorCredentials.putBoolean(IS_REMEMBER_ME, false);
        editorCredentials.commit();
    }

    public boolean isRememberMe() {
        return prefCredentials.getBoolean(IS_REMEMBER_ME, false);
    }

    public void setUserSession(String accessToken, String uName, String expiresIn, String refreshToken, String refreshExpiresIn) {
        editor.putString(AccessToken, accessToken);
        editor.putString(UName, uName);
        editor.putString(ExpiresIn, expiresIn);
        editor.putString(RefreshToken, refreshToken);
        editor.putString(RefreshExpiresIn, refreshExpiresIn);
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }

    public HashMap<String, String> getTokenDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(AccessToken, pref.getString(AccessToken, null));
        user.put(UName, pref.getString(UName, null));
        user.put(ExpiresIn, pref.getString(ExpiresIn, null));
        user.put(RefreshToken, pref.getString(RefreshToken, null));
        user.put(RefreshExpiresIn, pref.getString(RefreshExpiresIn, null));

        return user;
    }

    public void setUserCredentials(String user_id, String user_email, String user_name, String first_name, String last_name, String other_name, String phone_no) {
        editor.putString(UserID, user_id);
        editor.putString(UserEmail, user_email);
        editor.putString(UserName, user_name);
        editor.putString(FirstName, first_name);
        editor.putString(LastName, last_name);
        editor.putString(OtherName, other_name);
        editor.putString(PhoneNumber, phone_no);
        editor.apply();
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(UserID, pref.getString(UserID, null));
        user.put(UserEmail, pref.getString(UserEmail, null));
        user.put(UserName, pref.getString(UserName, null));
        user.put(FirstName, pref.getString(FirstName, null));
        user.put(LastName, pref.getString(LastName, null));
        user.put(OtherName, pref.getString(OtherName, null));
        user.put(PhoneNumber, pref.getString(PhoneNumber, null));

        return user;
    }

    public Long getTokenTime() {
        return pref.getLong(tokenRenewTime, System.currentTimeMillis());
    }

    public void setTokenTime() {
        editor.putLong(tokenRenewTime, System.currentTimeMillis());
        editor.apply();
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
