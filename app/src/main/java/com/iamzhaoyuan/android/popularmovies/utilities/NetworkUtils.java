package com.iamzhaoyuan.android.popularmovies.utilities;

import android.net.Uri;

import com.iamzhaoyuan.android.popularmovies.BuildConfig;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public final class NetworkUtils {
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;
    private static final String THE_MOVIE_DB_URL_POPULAR =
            "https://api.themoviedb.org/3/movie/popular";
    private static final String THE_MOVIE_DB_URL_TOP_RATED =
            "https://api.themoviedb.org/3/movie/top_rated";
    private static final String PARAM_API_KEY =
            "api_key";
    private static final String THE_MOVIE_IMAGE_BASE =
            "https://image.tmdb.org/t/p/";
    private static final String THE_MOVIE_IMAGE_POSTER_DEFAULT_SIZE =
            "w185";

    public static String getUrlStringByType(int type) {
        String baseUrl = type == POPULAR ? THE_MOVIE_DB_URL_POPULAR : THE_MOVIE_DB_URL_TOP_RATED;
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.THEMOVIEDB_API_KEY).build();
        return builtUri.toString();
    }

    public static String getResponseFromHttpUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getPosterUrlBySizeAndFilePath(String size, String filePath) {
        Timber.i(filePath);
        String filePathWithoutPrefix = filePath.substring(1);
        Uri builtUrl = Uri.parse(THE_MOVIE_IMAGE_BASE).buildUpon()
                .appendPath(size).appendPath(filePathWithoutPrefix).build();
        return builtUrl.toString();
    }

    public static String getPosterUrlByFilePath(String filePath) {
        return getPosterUrlBySizeAndFilePath(THE_MOVIE_IMAGE_POSTER_DEFAULT_SIZE, filePath);
    }

}
