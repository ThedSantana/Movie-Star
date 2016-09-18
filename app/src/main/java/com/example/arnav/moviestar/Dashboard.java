package com.example.arnav.moviestar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    CustomListViewAdapter customListViewAdapter;
    ArrayList<String> arrayListMovies;
    ListView listViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listViewMovies = (ListView)findViewById(R.id.listview_movies);

        AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(this, customListViewAdapter, listViewMovies);
        asyncTaskPopulateDashboard.execute();
    }
}
