package com.example.island_escape_mada.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.island_escape_mada.fragments.LoginFragment;
import com.example.island_escape_mada.fragments.RegistrationFragment;

import com.example.island_escape_mada.R;

public class AuthActivity extends AppCompatActivity {


    private boolean isLoginFragmentShown = true;
    private TextView tvSwitchForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Load the LoginFragment initially
        if (savedInstanceState == null) {
            showLoginFragment();
        }

        tvSwitchForm = findViewById(R.id.tvSwitchForm);
        updateSwitchButtonText();

        tvSwitchForm.setOnClickListener(v -> {
            // Toggle between LoginFragment and RegistrationFragment
            if (isLoginFragmentShown) {
                showRegistrationFragment();
            } else {
                showLoginFragment();
            }

            // Update the isLoginFragmentShown flag after fragment change
            isLoginFragmentShown = !isLoginFragmentShown;

            // Update the switch button text after fragment change
            updateSwitchButtonText();
        });
    }

    private void updateSwitchButtonText() {
        if (isLoginFragmentShown) {
            tvSwitchForm.setText(getString(R.string.create_account));
        } else {
            tvSwitchForm.setText(getString(R.string.already_have_account));
        }
    }

    public void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, loginFragment)
                .commit();
    }

    public void showRegistrationFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new RegistrationFragment())
                .commit();
    }
}

