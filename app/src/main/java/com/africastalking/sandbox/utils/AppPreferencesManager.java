package com.africastalking.sandbox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Lawrence on 10/29/15.
 */
public class AppPreferencesManager {

    public static final String PREF_INTRODUCTION = "pref_introduction_key";

    public static final String PREF_SKIP_SIGNUP = "pref_skip_sign_up_key";

    public static final String PREF_SIGNUP = "pref_sign_up_key";


    public static boolean isIntroductionDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_INTRODUCTION, false);
    }

    public static void markIntroductionDone(final Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_INTRODUCTION, true).commit();
    }


    public static boolean isSignUpFormShown(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_SIGNUP, false);
    }

    public static void markSignUpFormShown(final Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_SIGNUP, true).commit();
    }


}
