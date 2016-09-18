package com.example.arnav.moviestar;

/**
 * Created by Arnav on 14/09/2016.
 */
public class DatasetMovies {

    private String mMovieTitle;
    private String mOverview;
    private String mPopularity;
    private String mVoteRating;

    public DatasetMovies(String movieTitle, String overview, String popularity, String rating){
        this.mMovieTitle = movieTitle;
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
}
