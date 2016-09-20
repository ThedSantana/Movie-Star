package com.example.arnav.moviestar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Arnav on 18/09/2016.
 */
public class CustomExpandableListViewAdapter extends BaseExpandableListAdapter {

    Context context;
    private List<String> headings;
    private HashMap<String, List<String>> hashMapChildren;

    CustomExpandableListViewAdapter(Context context, List<String> headings, HashMap<String, List<String>> hashMapChildren) {
        this.context = context;
        this.headings = headings;
        this.hashMapChildren = hashMapChildren;
    }

    @Override
    public int getGroupCount() {
        return this.headings.size();
    }

    @Override
    public int getChildrenCount(int i) {
//        return hashMapChildren.get(this.headings.get(i)).size();
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return this.headings.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.hashMapChildren.get(this.headings.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String movieTitle = (String) this.getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_view_item_parent, null);
        }

        TextView textViewMovieTitle = (TextView) view.findViewById(R.id.textViewMovieTitleItem);
        textViewMovieTitle.setText(movieTitle);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String overview = (String)this.getChild(i, i1);
        String popularity = (String)this.getChild(i, i1 + 1);
        String voteRating = (String)this.getChild(i, i1 + 2);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_view_item_child, null);
        }

        TextView textViewOverview = (TextView) view.findViewById(R.id.textViewOverviewChildItem);
        TextView textViewPopularity = (TextView) view.findViewById(R.id.textViewPopularityItem);
        TextView textViewVoteRating = (TextView) view.findViewById(R.id.textViewVoteRatingItem);

        textViewOverview.setText(overview);
        textViewPopularity.setText(popularity);
        textViewVoteRating.setText(voteRating);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
