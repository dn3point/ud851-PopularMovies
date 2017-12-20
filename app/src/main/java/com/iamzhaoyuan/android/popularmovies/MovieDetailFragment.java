package com.iamzhaoyuan.android.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamzhaoyuan.android.popularmovies.entity.Movie;
import com.iamzhaoyuan.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    @BindView(R.id.tv_detail_title)
    TextView mTitleTextView;
    @BindView(R.id.tv_detail_overview)
    TextView mOverviewTextView;
    @BindView(R.id.tv_detail_rating)
    TextView mRatingTextView;
    @BindView(R.id.tv_detail_release)
    TextView mReleaseTextView;
    @BindView(R.id.iv_detail_poster)
    ImageView mPosterImageView;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_SUBJECT)) {
            Movie movie = intent.getParcelableExtra(Intent.EXTRA_SUBJECT);
            mTitleTextView.setText(movie.getOriginalTitle());
            mOverviewTextView.setText(movie.getOverview());
            mRatingTextView.setText(String.valueOf(movie.getAvgRating()));
            mReleaseTextView.setText(movie.getReleaseDateStr());
            Picasso.with(getActivity())
                    .load(NetworkUtils.getPosterUrlByFilePath(movie.getThumbnailUrl()))
                    .into(mPosterImageView);
        } else {
            Timber.d("No intent.");
        }

        return rootView;
    }

}
