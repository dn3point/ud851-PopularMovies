package com.iamzhaoyuan.android.popularmovies.service;

import android.os.AsyncTask;

import com.iamzhaoyuan.android.popularmovies.entity.Movie;
import com.iamzhaoyuan.android.popularmovies.utilities.NetworkUtils;
import com.iamzhaoyuan.android.popularmovies.utilities.TheMovieDBJsonUtils;

import org.json.JSONException;

import java.io.IOException;

import timber.log.Timber;

public class FetchMoviesTask extends AsyncTask<Integer, Void, Movie[]> {
    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onPreExecute();
        }
    }

    @Override
    protected Movie[] doInBackground(Integer... params) {
        if (params == null) {
            Timber.d("No type given.");
            return null;
        }

        int type = params[0];
        String url = NetworkUtils.getUrlStringByType(type);
        Timber.i(url);

        try {
            String response = NetworkUtils.getResponseFromHttpUrl(url);
            return TheMovieDBJsonUtils.getMovieArrayFromJson(response);
        } catch (IOException | JSONException e) {
            Timber.e(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        if (mListener != null) {
            mListener.onPostExecute(movies);
        }
    }

    public interface Listener {
        void onPreExecute();

        void onPostExecute(Movie[] movies);
    }
}