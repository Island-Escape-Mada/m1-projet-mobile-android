package com.example.island_escape_mada.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.fragments.ListFragment;

public class HistoricalPlacesActivity extends AppCompatActivity {

    public String infoUrl;
    public  HistoricalPlacesActivity(){
        super(R.layout.activity_historical_places);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.infoUrl = getIntent().getStringExtra("infoUrl");

        if (savedInstanceState == null) {
            ListFragment listFragment = new ListFragment(infoUrl);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container_view, listFragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        // get list fragment
        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (fragment != null && fragment.isVisible()) {
            if (fragment.canWebViewGoBack()) {
                fragment.goBackInWebView();
            } else {
                super.onBackPressed();
            }
        }
    }
}