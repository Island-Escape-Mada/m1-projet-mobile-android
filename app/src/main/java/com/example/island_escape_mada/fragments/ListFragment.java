package com.example.island_escape_mada.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.utility.CustomWebView;
import com.example.island_escape_mada.utility.FetchHtmlTask;

public class ListFragment extends Fragment implements FetchHtmlTask.FetchHtmlListener{

    private ProgressBar progressBar;
    private WebView webView;
    private String infoUrl;

    public ListFragment(String infoUrl) {
        super(R.layout.fragment_list);
        this.infoUrl = infoUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        webView = rootView.findViewById(R.id.webview_global_list);
        progressBar = rootView.findViewById(R.id.progressBar_global_list);

        WebView.setWebContentsDebuggingEnabled(true);

        // webview
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        /**
         * allow the web view to load template in the same current web view
         */
        webView.setWebViewClient(new CustomWebView());

        // progress bar
        progressBar.setVisibility(View.VISIBLE);
        new FetchHtmlTask(this).execute(getString(R.string.api_url) + infoUrl);

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

    public boolean canWebViewGoBack() {
        return webView != null && webView.canGoBack();
    }

    public void goBackInWebView() {
        if (webView != null) {
            webView.goBack();
        }
    }
}