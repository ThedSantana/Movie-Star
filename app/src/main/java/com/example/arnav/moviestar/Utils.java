package com.example.arnav.moviestar;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

/**
 * Created by Arnav on 14/09/2016.
 */
public class Utils {

    public static String API_KEY = "772990e94ae292eeb48b556347027a4e";
    public static String API_READ_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NzI5OTBlOTRhZTI5MmVlYjQ4YjU1NjM0NzAyN2E0ZSIsInN1YiI6IjU3ZDkwZTdlYzNhMzY4NTQ5MzAwNGNhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.mbvfN8itAMp_RWzbR8pvolSggZRM1eEr7HvF9h92Nho";
    public static String APP_NAME = "Movie Star";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
