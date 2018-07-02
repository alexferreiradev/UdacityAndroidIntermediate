package com.alex.popularmovies.app.util;

import android.content.Context;
import android.widget.ImageView;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Alex on 10/12/2017.
 */

final public class MovieImageUtil {
	public static final String IMAGE_LENGTH_W_185 = "w185";
	public static final String IMAGE_LENGTH_W_92 = "w92";

	private static final String MOVIE_DB_BASE_IMAGE_PATH = "http://image.tmdb.org/t/p/";

	@SuppressWarnings("StringBufferReplaceableByString")
	public static void setImageViewWithPicasso(ImageView imageView, Context context, Movie movie, String imageLength) {
		String path = new StringBuilder(MOVIE_DB_BASE_IMAGE_PATH)
				.append(imageLength).append("/")
				.append(movie.getPosterPath()).toString();

		Picasso.with(context).load(path).placeholder(context.getResources().getDrawable(R.drawable.ic_movie_loading)).error(context.getResources().getDrawable(R.drawable.ic_movie_not_loaded)).into(imageView);
	}
}
