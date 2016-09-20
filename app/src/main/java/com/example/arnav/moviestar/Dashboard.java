package com.example.arnav.moviestar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        expandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);

        AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(this, expandableListView);
        asyncTaskPopulateDashboard.execute();
    }
}
