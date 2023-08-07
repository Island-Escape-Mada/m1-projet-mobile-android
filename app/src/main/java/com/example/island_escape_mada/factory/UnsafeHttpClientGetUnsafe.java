package com.example.island_escape_mada.factory;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class UnsafeHttpClientGetUnsafe {

    public static OkHttpClient getUnsafeOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(TrustAllSSLSocketFactory.create(), new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        });
        return builder.build();
    }
}
