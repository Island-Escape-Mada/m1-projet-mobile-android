package com.example.island_escape_mada.factory;

import com.example.island_escape_mada.helpers.TrustAllCertificates;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustAllSSLSocketFactory {
    public static SSLSocketFactory create() {
        try {
            TrustManager[] trustAllCertificates = new TrustManager[]{new TrustAllCertificates()};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
