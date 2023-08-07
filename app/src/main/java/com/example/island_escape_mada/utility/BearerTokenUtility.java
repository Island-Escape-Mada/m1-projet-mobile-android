package com.example.island_escape_mada.utility;

import android.content.Context;

import com.example.island_escape_mada.R;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BearerTokenUtility {

    public static void addBearerTokenToRequest(OkHttpClient client) {
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Headers modifiedHeaders = originalRequest.headers().newBuilder()
                        .add("Authorization", "Bearer " + " aaa")
                        .build();

                Request modifiedRequest = originalRequest.newBuilder()
                        .headers(modifiedHeaders)
                        .build();

                return chain.proceed(modifiedRequest);
            }
        });
    }
}
