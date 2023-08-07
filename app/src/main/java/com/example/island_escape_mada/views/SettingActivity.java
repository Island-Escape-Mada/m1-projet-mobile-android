package com.example.island_escape_mada.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.island_escape_mada.R;
import com.example.island_escape_mada.factory.TrustAllSSLSocketFactory;
import com.example.island_escape_mada.helpers.SharedPreferenceHelper;
import com.example.island_escape_mada.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferenceHelper preferenceHelper;
//    private ImageView avatarImageView;
    private EditText usernameEditText;
    private Button btnUpdateInfo;
    private Switch switchCameraPermission;
    private Switch switchLocationPermission;
    private Switch switchInternetPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferenceHelper = new SharedPreferenceHelper(this);

//        avatarImageView = findViewById(R.id.image_avatar);
        usernameEditText = findViewById(R.id.edit_username);
        btnUpdateInfo = findViewById(R.id.btn_update_info);

        String username = preferenceHelper.getUser().getUsername();
//        String avatarUrl = preferenceHelper.getAvatarUrl();

        usernameEditText.setText(username);
//        Glide.with(this).load(avatarUrl).into(avatarImageView);

        switchCameraPermission = findViewById(R.id.switch_camera_permission);
        switchLocationPermission = findViewById(R.id.switch_location_permission);
        switchInternetPermission = findViewById(R.id.switch_internet_permission);

        try {
            setSwitchesFromUserSettings(preferenceHelper.getUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }

    private void logoutUser() {
        preferenceHelper.clearUserData();

        // Redirect the user to the Login screen
        Intent intent = new Intent(SettingActivity.this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void updateUserInfo() {
        String updatedUsername = usernameEditText.getText().toString().trim();
        User user = preferenceHelper.getUser();

        String API_URL = getString(R.string.api_url);

        boolean isCameraEnabled = switchCameraPermission.isChecked();
        boolean isLocationEnabled = switchLocationPermission.isChecked();
        boolean isInternetEnabled = switchInternetPermission.isChecked();

        JSONObject settings = new JSONObject();
        JSONObject privacy = new JSONObject();
        JSONObject favorites = new JSONObject();
        JSONObject permissions = new JSONObject();
        try {
            permissions.put("camera", isCameraEnabled);
            permissions.put("location", isLocationEnabled);
            permissions.put("internet", isInternetEnabled);
            settings.put("privacy", privacy);
            settings.put("favorites", favorites);
            settings.put("permissions", permissions);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String apiUrl = API_URL + "api/settings/update";

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("id", user.getId());
            requestObject.put("settings", settings);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            user.setSettings(settings);
                            preferenceHelper.saveUser(user);
                            Toast.makeText(SettingActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null && error.networkResponse != null) {
                            // Handle error response from the server if needed
                            String errorMessage = new String(error.networkResponse.data);
                            Toast.makeText(SettingActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //by pass ssl certificate check
        HttpsURLConnection.setDefaultSSLSocketFactory(TrustAllSSLSocketFactory.create());
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void setSwitchesFromUserSettings(User user) throws JSONException {
        if (user != null && user.getSettings() != null && user.getSettings().get("permissions")!= null) {
            try {
                JSONObject permissionsObject = (JSONObject) user.getSettings().get("permissions");

                boolean isCameraEnabled = permissionsObject.getBoolean("camera");
                boolean isLocationEnabled = permissionsObject.getBoolean("location");
                boolean isInternetEnabled = permissionsObject.getBoolean("internet");

                switchCameraPermission.setChecked(isCameraEnabled);
                switchLocationPermission.setChecked(isLocationEnabled);
                switchInternetPermission.setChecked(isInternetEnabled);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

