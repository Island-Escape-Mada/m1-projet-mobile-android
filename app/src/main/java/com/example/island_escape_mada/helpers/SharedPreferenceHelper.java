package com.example.island_escape_mada.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.island_escape_mada.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_SETTINGS = "settings";

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

        editor.putString(KEY_USER_ID, user.getId());

        if (user.getSettings() != null) {
            editor.putString(KEY_SETTINGS, user.getSettings().toString());
        } else {
            editor.putString(KEY_SETTINGS, null);
        }

        editor.apply();
    }

    public User getUser() {
        String username = preferences.getString(KEY_USERNAME, null);
        String userId = preferences.getString(KEY_USER_ID, null);
        String permissionsStr = preferences.getString(KEY_SETTINGS, null);

        User user = new User(username);
        user.setId(userId);

        if (permissionsStr != null) {
            try {
                JSONObject permissions = new JSONObject(permissionsStr);
                user.setSettings(permissions);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_SETTINGS);
        editor.apply();
    }
}
