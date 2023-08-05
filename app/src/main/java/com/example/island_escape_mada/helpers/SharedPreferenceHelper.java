package com.example.island_escape_mada.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.island_escape_mada.models.User;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    private SharedPreferences preferences;

    public SharedPreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.apply();
    }

    public User getUser() {
        String username = preferences.getString(KEY_USERNAME, null);
        return new User(username);
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_USERNAME);
        editor.apply();
    }
}

