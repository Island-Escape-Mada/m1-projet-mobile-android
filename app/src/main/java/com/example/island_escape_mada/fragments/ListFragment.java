package com.example.island_escape_mada.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

        WebView.setWebContentsDebuggingEnabled(true);

        // webview
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // webSettings.setAllowFileAccess(true);

        // webView.setWebChromeClient(new WebChromeClient());

        /**
         * load template in the same current web view
         */
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Prevent the default behavior of opening URLs externally
                return false;
            }
        });


        // progress bar
        progressBar.setVisibility(View.VISIBLE);
        new FetchHtmlTask(this).execute(getString(R.string.api_url) + "list-info?info_type=" + infoType);

        return rootView;
    }

    @Override
    public void onHtmlFetched(String htmlContent) {
        if (htmlContent != null){
            String baseUrl = "file:///android_asset/";
            System.out.println(htmlContent);
            // webView.loadUrl("file:///android_asset/about_mada.html");
            webView.loadDataWithBaseURL(baseUrl, htmlContent, "text/html", "UTF-8", null);
            // Inject the local JavaScript file into the WebView content
            webView.loadUrl("javascript:var script = document.createElement('script'); " +
                    "script.src = 'file:///android_asset/js/topimage.js'; " +
                    "document.head.appendChild(script);");
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