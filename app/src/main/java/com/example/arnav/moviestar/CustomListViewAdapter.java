package com.example.arnav.moviestar;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arnav on 15/09/2016.
 */
public class CustomListViewAdapter extends ArrayAdapter<DatasetMovies> {


    public CustomListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View viewCustom = layoutInflater.inflate(R.layout.list_view_item, parent, false);

        DatasetMovies datasetMovies = getItem(position);
        TextView textViewMovieTitle = (TextView)viewCustom.findViewById(R.id.textViewMovieTitleItem);
        TextView textViewOverview = (TextView)viewCustom.findViewById(R.id.textViewOverviewItem);
        TextView textViewPopularity = (TextView)viewCustom.findViewById(R.id.textViewPopularityItem);
        TextView textViewVoteRating = (TextView)viewCustom.findViewById(R.id.textViewVoteRatingItem);

        textViewMovieTitle.setText(datasetMovies.getmMovieTitle());
        textViewOverview.setText(datasetMovies.getmOverview());
        textViewPopularity.setText(datasetMovies.getmPopularity());
        textViewVoteRating.setText(datasetMovies.getmVoteRating());

        return viewCustom;
    }
}
