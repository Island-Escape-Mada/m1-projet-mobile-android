package com.example.island_escape_mada.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.fragments.ListFragment;

public class NaturalWonderActivity extends AppCompatActivity {

    public NaturalWonderActivity(){
        super(R.layout.activity_natural_wonder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            ListFragment listFragment = new ListFragment("natural");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container_view, listFragment);
            transaction.commit();
        }
    }
}