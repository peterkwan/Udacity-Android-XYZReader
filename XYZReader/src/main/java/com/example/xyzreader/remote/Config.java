package com.example.xyzreader.remote;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {
    public static final URL BASE_URL;
    private static String TAG = Config.class.toString();

    static {
        try {
            BASE_URL = new URL("https://go.udacity.com/xyz-reader-json" );
        } catch (MalformedURLException ignored) {
            Log.e(TAG, "Please check your internet connection.",  ignored);
            throw new RuntimeException("Please check your internet connection.");
        }
    }
}
