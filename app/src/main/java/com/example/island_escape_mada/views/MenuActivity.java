package com.example.island_escape_mada.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.island_escape_mada.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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

        about.setOnClickListener(onMenuButtonClick);
        natural.setOnClickListener(onMenuButtonClick);
        coast.setOnClickListener(onMenuButtonClick);
        place.setOnClickListener(onMenuButtonClick);
        hospitality.setOnClickListener(onMenuButtonClick);
        news.setOnClickListener(onMenuButtonClick);
        fabSettings.setOnClickListener(onMenuButtonClick);
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
                    startActivity(intent);
                    break;
                case R.id.btn_coast:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, CoastActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_historical_place:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, HistoricalPlacesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_hospitality:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, HospitalityActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_news:
                    // Action for button1 click
                    intent = new Intent(MenuActivity.this, LatestNewsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fab_settings:
                    intent = new Intent(MenuActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}