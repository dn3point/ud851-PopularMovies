package com.iamzhaoyuan.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iamzhaoyuan.android.popularmovies.R;
import com.iamzhaoyuan.android.popularmovies.entity.Movie;
import com.iamzhaoyuan.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {

    private final PosterAdapterOnCLickHandler mOnCLickHandler;
    private Movie[] mMovieData;
    private Context mContext;

    public PosterAdapter(Context context, PosterAdapterOnCLickHandler onCLickHandler) {
        mContext = context;
        mOnCLickHandler = onCLickHandler;
    }

    @Override
    public PosterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutId = R.layout.poster_item;
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);
        return new PosterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterAdapterViewHolder holder, int position) {
        Movie movie = mMovieData[position];
        String filePath = movie.getThumbnailUrl();
        String url = NetworkUtils.getPosterUrlByFilePath(filePath);
        Timber.i(url);
        Picasso.with(mContext).load(url).into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieData == null ? 0 : mMovieData.length;
    }

    public Movie[] getMovieData() {
        return mMovieData;
    }

    public void setMovieData(Movie[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    public interface PosterAdapterOnCLickHandler {
        void onClick(Movie movie);
    }

    public class PosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_poster)
        public ImageView mPosterImageView;

        public PosterAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieData[adapterPosition];
            mOnCLickHandler.onClick(movie);
        }
    }
}
