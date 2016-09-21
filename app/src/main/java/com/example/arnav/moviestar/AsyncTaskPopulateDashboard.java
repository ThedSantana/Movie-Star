package com.example.arnav.moviestar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ExpandableListView;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arnav on 14/09/2016.
 */
public class AsyncTaskPopulateDashboard extends AsyncTask<Void, DatasetMovies, Void> {

    final String LOG_TAG;
    Context contextPopulateDashboard;
    Activity activityPopulateDashboard;
    ProgressDialog progressDialogPopulateDashboard;

    String movieCategoryType;
    final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    String MOVIE_DB_TYPE = "popular?";
    final String API_KEY_PARAM = "api_key";

    URL url = null;
    HttpURLConnection httpURLConnection = null;
    BufferedReader bufferedReader = null;

    String movieJson;

    CustomExpandableListViewAdapter customExpandableListViewAdapter;
    ExpandableListView expandableListView;

    List<String> headings = new ArrayList<>();
    HashMap<String, List<String>> hashMapChildren = new HashMap<>();


    AsyncTaskPopulateDashboard(Context context, ExpandableListView expandableListView, String movieCategoryType){
        this.contextPopulateDashboard = context;
        this.activityPopulateDashboard = (Activity)context;
        this.expandableListView = expandableListView;
        this.movieCategoryType = movieCategoryType;
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

        switch (movieCategoryType){
            case "Popular Movies":
                MOVIE_DB_TYPE = "popular?";
                break;
            case "Top Rated Movies":
                MOVIE_DB_TYPE = "top_rated?";
                break;
            case "Upcoming Movies":
                MOVIE_DB_TYPE = "upcoming?";
                break;
            default:
                MOVIE_DB_TYPE = "popular?";
        }
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
                prepareListData(datasetMovies, i);
                publishProgress();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void prepareListData(DatasetMovies datasetMovies, int count) {

        headings.add(datasetMovies.getmMovieTitle());
        List<String> details = new ArrayList<String>();
        details.add(datasetMovies.getmOverview());
        details.add(datasetMovies.getmPopularity());
        details.add(datasetMovies.getmVoteRating());

        hashMapChildren.put(headings.get(count), details);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialogPopulateDashboard.dismiss();
    }

    @Override
    protected void onProgressUpdate(DatasetMovies... datasetMovies) {
        customExpandableListViewAdapter = new CustomExpandableListViewAdapter(contextPopulateDashboard, headings, hashMapChildren);
        expandableListView.setAdapter(customExpandableListViewAdapter);
    }
}
