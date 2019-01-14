package com.thcreate.vegsurveyassistant.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.thcreate.vegsurveyassistant.BasicApp;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    private static String PREF_NAME = "VEG_SURVEY_ASSISTANT_PREF";

    private static String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";

    private static String USER_ID = "USER_ID";

    public static void login(int userId){
        SharedPreferences sharedPreferences = BasicApp.getAppliction().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, userId);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public static void logout(){
        SharedPreferences sharedPreferences = BasicApp.getAppliction().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }

    public static Boolean isLoggedIn(){
        SharedPreferences sharedPreferences = BasicApp.getAppliction().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static int getLoggedInUserId(){
        SharedPreferences sharedPreferences = BasicApp.getAppliction().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_ID, 0);
    }

}
