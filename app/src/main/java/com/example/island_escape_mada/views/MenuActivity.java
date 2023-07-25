package com.example.island_escape_mada.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.island_escape_mada.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    private void init(){
        Button about = findViewById(R.id.btn_about_madagascar);
        Button natural = findViewById(R.id.btn_natural_wonder);
        Button coast = findViewById(R.id.btn_coast);
        Button place = findViewById(R.id.btn_historical_place);
        Button hospitality = findViewById(R.id.btn_hospitality);
        Button news = findViewById(R.id.btn_news);

        about.setOnClickListener(onMenuButtonClick);
        natural.setOnClickListener(onMenuButtonClick);
        coast.setOnClickListener(onMenuButtonClick);
        place.setOnClickListener(onMenuButtonClick);
        hospitality.setOnClickListener(onMenuButtonClick);
        news.setOnClickListener(onMenuButtonClick);
    }

    /**
     * menu button action listener
     */
    private View.OnClickListener onMenuButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // open related menu fragment
            switch (view.getId()) {
                case R.id.btn_about_madagascar:
                    // Action for button1 click
                    Intent intent = new Intent(MenuActivity.this, AboutMadaActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_natural_wonder:
                    // Action for button1 click
                    Toast.makeText(MenuActivity.this, "btn_natural_wonder", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_coast:
                    // Action for button1 click
                    Toast.makeText(MenuActivity.this, "btn_coast", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_historical_place:
                    // Action for button1 click
                    Toast.makeText(MenuActivity.this, "btn_historical_place", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_hospitality:
                    // Action for button1 click
                    Toast.makeText(MenuActivity.this, "btn_hospitality", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_news:
                    // Action for button1 click
                    Toast.makeText(MenuActivity.this, "btn_news", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}