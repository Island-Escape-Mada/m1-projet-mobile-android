package com.example.island_escape_mada.views;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.island_escape_mada.FetchHtmlTask;
import com.example.island_escape_mada.R;

public class AboutMadaActivity extends AppCompatActivity implements FetchHtmlTask.FetchHtmlListener {

    private ProgressBar progressBar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_mada);

        webView = findViewById(R.id.webview_about_mada);
        progressBar = findViewById(R.id.progressBar);

        // webview
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // progress bar
        progressBar.setVisibility(View.VISIBLE);
        new FetchHtmlTask(this).execute(getString(R.string.api_url) + "about-mada");
    }

    @Override
    public void onHtmlFetched(String htmlContent) {
        if (htmlContent != null){
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
            // hide the progress bar
            progressBar.setVisibility(View.GONE);
        }
    }
}