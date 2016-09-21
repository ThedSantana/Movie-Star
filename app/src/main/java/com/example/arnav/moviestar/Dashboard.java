package com.example.arnav.moviestar;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ExpandableListView expandableListView;
    Spinner spinnerType;
    String movieCategoryType;
    String DEFAULT_MOVIE_CATEGORY = "Popular Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //References
        expandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
        spinnerType = (Spinner)findViewById(R.id.spinnerDashboard);

        //Getting MovieTypeCategory From Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        movieCategoryType = sharedPreferences.getString("movie_type", "Popular Movies");

        //Spinner
        spinnerType.setOnItemSelectedListener(this);
        getMovieCategoryList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getMovieCategoryList());
        spinnerType.setAdapter(spinnerAdapter);

        //Network Connection Check
        if(Utils.isNetworkConnected(this)){
            AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(this, expandableListView, getMovieCategory());
            asyncTaskPopulateDashboard.execute();
        }
        else
            showInternetConnectionError();
    }

    private List<String> getMovieCategoryList() {

        List<String> list = new ArrayList<>();
        list.add("Popular Movies");
        list.add("Top Rated Movies");
        list.add("Upcoming Movies");

        return list;
    }

    private void showInternetConnectionError() {
        final AlertDialog.Builder alertDialog = new AlertDialog
                .Builder(Dashboard.this)
                .setTitle("No Internet Connection")
                .setMessage("This application requires Internet to work. Please switch on your Internet Connection and press 'Retry'.")
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Utils.isNetworkConnected(Dashboard.this)){
                            AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(Dashboard.this, expandableListView, getMovieCategory());
                            asyncTaskPopulateDashboard.execute();
                        }
                        else
                            showInternetConnectionError();
                    }
                });
        alertDialog.show();
    }

    private String getMovieCategory() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("movie_type", DEFAULT_MOVIE_CATEGORY);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String item = adapterView.getItemAtPosition(i).toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("movie_type", item);
        editor.apply();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_refresh:
                AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(Dashboard.this, expandableListView, getMovieCategory());
                asyncTaskPopulateDashboard.execute();
        }

        return super.onOptionsItemSelected(item);
    }
}
