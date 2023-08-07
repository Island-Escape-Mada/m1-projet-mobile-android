package com.example.island_escape_mada.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.factory.UnsafeHttpClientGetUnsafe;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getNotification();
        init();
    }

    private void init(){
        collapsingToolbarBehaviour();

        Button about = findViewById(R.id.btn_about_madagascar);
        Button natural = findViewById(R.id.btn_natural_wonder);
        Button coast = findViewById(R.id.btn_coast);
        Button place = findViewById(R.id.btn_historical_place);
        Button hospitality = findViewById(R.id.btn_hospitality);
        Button news = findViewById(R.id.btn_news);
        FloatingActionButton fabSettings = findViewById(R.id.fab_settings);
        FloatingActionButton fabSearch = findViewById(R.id.fab_search);

        about.setOnClickListener(onMenuButtonClick);
        natural.setOnClickListener(onMenuButtonClick);
        coast.setOnClickListener(onMenuButtonClick);
        place.setOnClickListener(onMenuButtonClick);
        hospitality.setOnClickListener(onMenuButtonClick);
        news.setOnClickListener(onMenuButtonClick);
        fabSettings.setOnClickListener(onMenuButtonClick);
        fabSearch.setOnClickListener(onMenuButtonClick);
    }

    /**
     * Manage collapsing toolbar behaviour
     */
    public void collapsingToolbarBehaviour(){
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.menu_collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));

        // collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandingToolbarLayoutTitleColor);
    }

    /**
     * menu button action listener
     */
    private View.OnClickListener onMenuButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // open related menu fragment
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btn_about_madagascar:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, AboutMadaActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_natural_wonder:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, NaturalWonderActivity.class);
                    intent.putExtra("infoUrl", "list-info?info_type=natural");
                    startActivity(intent);
                    break;
                case R.id.btn_coast:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, CoastActivity.class);
                    intent.putExtra("infoUrl", "list-info?info_type=beach");
                    startActivity(intent);
                    break;
                case R.id.btn_historical_place:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, HistoricalPlacesActivity.class);
                    intent.putExtra("infoUrl", "list-info?info_type=historical");
                    startActivity(intent);
                    break;
                case R.id.btn_hospitality:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, HospitalityActivity.class);
                    intent.putExtra("infoUrl", "list-info?info_type=hospitality");
                    startActivity(intent);
                    break;
                case R.id.btn_news:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, LatestNewsActivity.class);
                    intent.putExtra("infoUrl", "list-info?info_type=news");
                    startActivity(intent);
                    break;
                case R.id.fab_settings:
                    intent = new Intent(MenuActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fab_search:
                    intent = new Intent(MenuActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    /**
     * get and create notification on hot new content after successful login
     */
    private void getNotification(){
        String API_URL = getString(R.string.api_url) + "notification";
        OkHttpClient client = UnsafeHttpClientGetUnsafe.getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);

                        String notificationTitle = jsonObject.getString("notificationTitle");
                        String notificationText = jsonObject.getString("notificationText");
                        String activityClassName = jsonObject.getString("activityClassName");
                        String apiLink = jsonObject.getString("apiLink");

                        createNotification(notificationTitle, notificationText, activityClassName, apiLink);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Log.d("OkHttp", "JSON Response: " + jsonData);
                } else {
                    Log.e("OkHttp", "Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("OkHttp", "Request failed: " + e.getMessage());
            }
        });
    }

    private void createNotification(String notificationTitle, String notificationText, String activityClassName, String apiLink) throws ClassNotFoundException {
        Context context = this;

        Intent intent = new Intent(context, Class.forName("com.example.island_escape_mada.views." + activityClassName));
        intent.putExtra("infoUrl", apiLink);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the notification
        Notification notification = new NotificationCompat.Builder(context, "island_escape_notifications")
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        // Display the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
}