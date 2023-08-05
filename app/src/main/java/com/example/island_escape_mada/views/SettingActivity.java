package com.example.island_escape_mada.views;
import android.content.Intent;
import android.os.Bundle;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.helpers.SharedPreferenceHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferenceHelper preferenceHelper;
//    private ImageView avatarImageView;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

// Initialize SharedPreferenceHelper
        preferenceHelper = new SharedPreferenceHelper(this);

//        avatarImageView = findViewById(R.id.image_avatar);
        usernameEditText = findViewById(R.id.edit_username);

        String username = preferenceHelper.getUser().getUsername();
//        String avatarUrl = preferenceHelper.getAvatarUrl();

        usernameEditText.setText(username);
//        Glide.with(this).load(avatarUrl).into(avatarImageView);

        // Find the Logout button and set its click listener
        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
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
}

