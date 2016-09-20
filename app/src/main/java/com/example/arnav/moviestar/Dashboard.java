package com.example.arnav.moviestar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

public class Dashboard extends AppCompatActivity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        expandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);

        if(Utils.isNetworkConnected(this)){
            AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(this, expandableListView);
            asyncTaskPopulateDashboard.execute();
        }
        else
            showInternetConnectionError();
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
                            AsyncTaskPopulateDashboard asyncTaskPopulateDashboard = new AsyncTaskPopulateDashboard(Dashboard.this, expandableListView);
                            asyncTaskPopulateDashboard.execute();
                        }
                        else
                            showInternetConnectionError();
                    }
                });
        alertDialog.show();
    }
}
