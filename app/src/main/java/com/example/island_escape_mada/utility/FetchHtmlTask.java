package com.example.island_escape_mada.utility;

import android.os.AsyncTask;

import com.example.island_escape_mada.factory.UnsafeHttpClientGetUnsafe;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchHtmlTask extends AsyncTask<String, Void, String> {

    private FetchHtmlListener listener;

    public FetchHtmlTask(FetchHtmlListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        if (urls.length == 0) {
            return null;
        }

        String url = urls[0];
        OkHttpClient client = UnsafeHttpClientGetUnsafe.getUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String htmlContent) {
        if (listener != null) {
            listener.onHtmlFetched(htmlContent);
        }
    }

    public interface FetchHtmlListener {
        void onHtmlFetched(String htmlContent);
    }
}
