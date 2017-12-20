package com.iamzhaoyuan.android.popularmovies.utilities;

import com.iamzhaoyuan.android.popularmovies.entity.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public final class TheMovieDBJsonUtils {

    public static Movie[] getMovieArrayFromJson(String moviesJsonStr) throws JSONException {
        final String NODE_RESULTS = "results";
        final String NODE_VOTE_AVG = "vote_average";
        final String NODE_ORI_TITLE = "original_title";
        final String NODE_OVERVIEW = "overview";
        final String NODE_RELEASE_DATE = "release_date";
        final String NODE_POSTER_PATH = "poster_path";
        final String NODE_SUCCESS = "success";
        final String NODE_STATUS_MSG = "status_message";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        if (moviesJson.has(NODE_SUCCESS)) {
            Timber.d(moviesJson.getString(NODE_STATUS_MSG));
            return null;
        }

        Movie[] results;

        JSONArray moviesArray = moviesJson.getJSONArray(NODE_RESULTS);

        results = new Movie[moviesArray.length()];

        for (int i = 0; i < results.length; i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            String title = movieObject.getString(NODE_ORI_TITLE);
            String overview = movieObject.getString(NODE_OVERVIEW);
            String postPath = movieObject.getString(NODE_POSTER_PATH);
            String release = movieObject.getString(NODE_RELEASE_DATE);
            double rating = movieObject.getDouble(NODE_VOTE_AVG);

            results[i] = new Movie(title, postPath, overview, rating, release);
        }

        return results;
    }
}
