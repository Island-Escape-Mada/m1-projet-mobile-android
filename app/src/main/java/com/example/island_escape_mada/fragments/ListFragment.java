package com.example.island_escape_mada.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.island_escape_mada.FetchHtmlTask;
import com.example.island_escape_mada.R;

public class ListFragment extends Fragment implements FetchHtmlTask.FetchHtmlListener{

    private ProgressBar progressBar;
    private WebView webView;
    private String infoType;

    public ListFragment(String infoType) {
        super(R.layout.fragment_list);
        this.infoType = infoType;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        webView = rootView.findViewById(R.id.webview_global_list);
        progressBar = rootView.findViewById(R.id.progressBar_global_list);

        // webview
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // progress bar
        progressBar.setVisibility(View.VISIBLE);
        new FetchHtmlTask(this).execute(getString(R.string.api_url) + "list-info?info_type=" + infoType);

        return rootView;
    }

    @Override
    public void onHtmlFetched(String htmlContent) {
        if (htmlContent != null){
            String baseUrl = "file:///android_asset/";
            webView.loadDataWithBaseURL(baseUrl, htmlContent, "text/html", "UTF-8", null);
            // hide the progress bar
            progressBar.setVisibility(View.GONE);
        }
    }
}