package com.iamzhaoyuan.android.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR =
            new Parcelable.Creator<Movie>() {

                @Override
                public Movie createFromParcel(Parcel parcel) {
                    return new Movie(parcel);
                }

                @Override
                public Movie[] newArray(int i) {
                    return new Movie[0];
                }
            };

    private String mOriginalTitle;
    private String mThumbnailUrl;
    private String mOverview;
    private double mAvgRating;
    private String mReleaseDateStr;

    public Movie(String originalTitle, String thumbnailUrl, String overview,
                 double avgRating, String releaseDateStr) {
        mOriginalTitle = originalTitle;
        mThumbnailUrl = thumbnailUrl;
        mOverview = overview;
        mAvgRating = avgRating;
        mReleaseDateStr = releaseDateStr;
    }

    private Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mThumbnailUrl = in.readString();
        mOverview = in.readString();
        mAvgRating = in.readDouble();
        mReleaseDateStr = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mThumbnailUrl);
        parcel.writeString(mOverview);
        parcel.writeDouble(mAvgRating);
        parcel.writeString(mReleaseDateStr);
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getAvgRating() {
        return mAvgRating;
    }

    public String getReleaseDateStr() {
        return mReleaseDateStr;
    }
}
