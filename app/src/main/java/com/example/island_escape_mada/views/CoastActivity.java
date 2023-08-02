package com.example.island_escape_mada.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.fragments.ListFragment;

public class CoastActivity extends AppCompatActivity {

    public CoastActivity() {
        super(R.layout.activity_coast);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            ListFragment listFragment = new ListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container_view, listFragment);
            transaction.commit();
        }
    }
}