package com.example.arnav.moviestar;

import android.graphics.Bitmap;

import java.util.Objects;

/**
 * Created by Arnav on 14/09/2016.
 */
public class DatasetMovies {

    private String mMovieTitle;
    private String mMoviePoster;
    private String mOverview;
    private String mPopularity;
    private String mVoteRating;

    public DatasetMovies(String movieTitle, String moviePoster, String overview, String popularity, String rating){
        this.mMovieTitle = movieTitle;
        this.mMoviePoster = moviePoster;
        this.mOverview = overview;
        this.mPopularity = popularity;
        this.mVoteRating = rating;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public String getmMoviePoster() {
        return mMoviePoster;
    }

    public void setmMoviePoster(String mMoviePoster) {
        this.mMoviePoster = mMoviePoster;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmPopularity() {
        return mPopularity;
    }

    public void setmPopularity(String mPopularity) {
        this.mPopularity = mPopularity;
    }

    public String getmVoteRating() {
        return mVoteRating;
    }

    public void setmVoteRating(String mVoteRating) {
        this.mVoteRating = mVoteRating;
    }

    public String[] getHeadingSet(){
        String[] headings = new String[2];
        headings[0] = getmMovieTitle();
        headings[1] = getmPopularity();
        headings[2] = getmVoteRating();

        return headings;
    }
}
