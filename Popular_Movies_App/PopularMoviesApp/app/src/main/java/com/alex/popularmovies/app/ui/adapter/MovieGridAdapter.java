package com.alex.popularmovies.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.util.MovieImageUtil;

import java.util.List;

/**
 * Created by Alex on 25/03/2017.
 */

public class MovieGridAdapter extends BaseAdapter {
    private List<Movie> movies;
    private Context context;

    public MovieGridAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_movies_grid, parent, false);
            convertView.setTag(convertView);
        } else {
            convertView = (View) convertView.getTag();
        }

        Movie movie = movies.get(position);
        ImageView moviePosterIV = convertView.findViewById(R.id.moviePosterIV);
        moviePosterIV.setContentDescription("Imagem: " + movie.getPosterPath());
        MovieImageUtil.setImageViewWithPicasso(moviePosterIV, context, movie, MovieImageUtil.IMAGE_LENGTH_W_185);

        return convertView;
    }
}
