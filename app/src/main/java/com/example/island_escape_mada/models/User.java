package com.example.island_escape_mada.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String id;
    private String username;
    private JSONObject settings;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("_id");
        username = jsonObject.getString("username");

        if (jsonObject.has("settings")) {
            settings = jsonObject.getJSONObject("settings");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JSONObject getSettings() {
        return settings;
    }

    public void setSettings(JSONObject permissions) {
        this.settings = permissions;
    }
}
