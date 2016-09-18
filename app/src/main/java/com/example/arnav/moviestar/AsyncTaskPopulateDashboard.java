package com.example.arnav.moviestar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Arnav on 14/09/2016.
 */
public class AsyncTaskPopulateDashboard extends AsyncTask<Void, DatasetMovies, Void> {

    final String LOG_TAG;
    Context contextPopulateDashboard;
    Activity activityPopulateDashboard;
    ProgressDialog progressDialogPopulateDashboard;

    final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    final String MOVIE_DB_TYPE = "popular?";
    final String API_KEY_PARAM = "api_key";

    URL url = null;
    HttpURLConnection httpURLConnection = null;
    BufferedReader bufferedReader = null;

    String movieJson;

    CustomListViewAdapter customListViewAdapter;
    ListView listViewMovies;

    AsyncTaskPopulateDashboard(Context context, CustomListViewAdapter customListViewAdapter, ListView listView){
        this.contextPopulateDashboard = context;
        this.activityPopulateDashboard = (Activity)context;
        this.customListViewAdapter = customListViewAdapter;
        this.listViewMovies = listView;
        LOG_TAG = Dashboard.class.getSimpleName();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialogPopulateDashboard = new ProgressDialog(activityPopulateDashboard);
        progressDialogPopulateDashboard.setTitle(Utils.APP_NAME);
        progressDialogPopulateDashboard.setMessage("Populating...");
        progressDialogPopulateDashboard.setIndeterminate(false);
        progressDialogPopulateDashboard.show();

        customListViewAdapter = new CustomListViewAdapter(contextPopulateDashboard, R.layout.list_view_item);
        listViewMovies.setAdapter(customListViewAdapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Uri uriBuilder = Uri.parse(MOVIE_DB_BASE_URL + MOVIE_DB_TYPE).buildUpon()
                            .appendQueryParameter(API_KEY_PARAM, Utils.API_KEY)
                            .build();

        try {
            url = new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            movieJson = stringBuilder.toString();
            Log.d(LOG_TAG, movieJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObjectRoot = new JSONObject(movieJson);
            JSONArray jsonArrayResults = jsonObjectRoot.optJSONArray("results");

            for(int i=0; i < jsonArrayResults.length(); i++){
                JSONObject jsonObject = jsonArrayResults.getJSONObject(i);

                String jsonMovieTitle = jsonObject.getString("title");
                String jsonOverview = jsonObject.getString("overview");
                String jsonPopularity = jsonObject.getString("popularity");
                String jsonVoteRating = jsonObject.getString("vote_average");

                DatasetMovies datasetMovies = new DatasetMovies(jsonMovieTitle, jsonOverview, jsonPopularity, jsonVoteRating);
                publishProgress(datasetMovies);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialogPopulateDashboard.dismiss();
    }

    @Override
    protected void onProgressUpdate(DatasetMovies... datasetMovies) {
        customListViewAdapter.add(datasetMovies[0]);
        customListViewAdapter.notifyDataSetChanged();
    }
}
