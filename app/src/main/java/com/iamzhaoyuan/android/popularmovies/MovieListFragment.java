package com.iamzhaoyuan.android.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.iamzhaoyuan.android.popularmovies.adapter.PosterAdapter;
import com.iamzhaoyuan.android.popularmovies.entity.Movie;
import com.iamzhaoyuan.android.popularmovies.service.FetchMoviesTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements PosterAdapter.PosterAdapterOnCLickHandler {

    private static final String KEY_MOVIE_DATA = "key_movies";
    private static final String KEY_SPINNER_POS = "key_spinner";
    private static final String KEY_RECYCLER_VIEW_POS = "key_rv_pos";

    @BindView(R.id.rv_poster)
    RecyclerView mPosterRecyclerView;
    @BindView(R.id.pb_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_error_message)
    TextView mErrorMessageTextView;

    private PosterAdapter mPosterAdapter;

    private int mType = 0;

    private FetchMoviesTask mMoviesTask;

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        if (mMoviesTask != null) {
            mMoviesTask.setListener(null);
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mPosterRecyclerView.setLayoutManager(layoutManager);
        mPosterRecyclerView.setHasFixedSize(true);
        mPosterAdapter = new PosterAdapter(getActivity(), this);
        mPosterRecyclerView.setAdapter(mPosterAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_type);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.action_type_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(mType);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (mType != pos) {
                    mType = pos;
                    mPosterAdapter.setMovieData(null);
                    loadMoviePoster();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(KEY_MOVIE_DATA, mPosterAdapter.getMovieData());
        outState.putInt(KEY_SPINNER_POS, mType);
        outState.putParcelable(KEY_RECYCLER_VIEW_POS,
                mPosterRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mType = savedInstanceState.getInt(KEY_SPINNER_POS);
            Movie[] movieData = (Movie[]) savedInstanceState.getParcelableArray(KEY_MOVIE_DATA);
            if (movieData != null) {
                mPosterAdapter.setMovieData(movieData);
            } else {
                loadMoviePoster();
            }
            Parcelable state = savedInstanceState.getParcelable(KEY_RECYCLER_VIEW_POS);
            mPosterRecyclerView.getLayoutManager().onRestoreInstanceState(state);
        } else {
            loadMoviePoster();
        }
    }

    private void loadMoviePoster() {
        showPosterDataView();
        mMoviesTask = new FetchMoviesTask();
        mMoviesTask.setListener(createListener());
        mMoviesTask.execute(mType);
    }

    private void showPosterDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mPosterRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mPosterRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Intent.EXTRA_SUBJECT, movie);
        startActivity(intent);
    }

    private FetchMoviesTask.Listener createListener() {
        return new FetchMoviesTask.Listener() {
            @Override
            public void onPreExecute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute(Movie[] movies) {
                mProgressBar.setVisibility(View.INVISIBLE);
                if (movies != null) {
                    showPosterDataView();
                    mPosterAdapter.setMovieData(movies);
                } else {
                    showErrorMessage();
                }
            }
        };
    }

}
