package com.example.island_escape_mada.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.island_escape_mada.R;
import com.example.island_escape_mada.factory.TrustAllSSLSocketFactory;
import com.example.island_escape_mada.helpers.SharedPreferenceHelper;
import com.example.island_escape_mada.models.User;
import com.example.island_escape_mada.views.MenuActivity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegistrationFragment extends Fragment {

    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;
    private String API_URL;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get the API URL from the resources
        API_URL = getString(R.string.api_url);

        etUsername = requireView().findViewById(R.id.etUsername);
        etPassword = requireView().findViewById(R.id.etPassword);
        etConfirmPassword = requireView().findViewById(R.id.etConfirmPassword);
        btnRegister = requireView().findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Make an API call to register user
        String url = API_URL + "api/auth/register";

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("username", username);
            requestObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful registration response
                        try {
                            String message = response.getString("message");
                            // Handle the success message accordingly
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                            JSONObject userObject = response.getJSONObject("user");
                            User user = new User(userObject);
                            SharedPreferenceHelper preferenceHelper = new SharedPreferenceHelper(requireContext());
                            preferenceHelper.setLoggedIn(true);
                            preferenceHelper.saveUser(user);

                            Intent intent = new Intent(requireContext(), MenuActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null) {
                            return;
                        }

                        String body;
                        //get status code here
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Toast.makeText(requireContext(), body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        return params;
                    }
                };

        //by pass ssl certificate check
        HttpsURLConnection.setDefaultSSLSocketFactory(TrustAllSSLSocketFactory.create());
        Volley.newRequestQueue(requireContext()).add(jsonObjectRequest);
    }
}
