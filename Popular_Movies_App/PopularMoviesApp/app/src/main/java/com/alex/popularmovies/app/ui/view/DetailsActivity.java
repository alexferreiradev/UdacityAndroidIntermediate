package com.alex.popularmovies.app.ui.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.MovieRepository;
import com.alex.popularmovies.app.ui.presenter.DetailPresenter;

public class DetailsActivity extends BaseActivity<Movie, DetailPresenter.View, DetailPresenter> implements DetailPresenter.View {

    private TextView tvName;
    private TextView tvRating;
    private ImageView ivMovieImage;
    private Movie mMovie;

    public static final String EXTRA_PARAM_MOVIE_ID = "Movie id";
    private long movieId = -1;
    private ToggleButton tbFavorite;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mPresenter = new DetailPresenter(this, this, savedInstanceState, new MovieRepository(this));
    }

    @Override
    public void initializeWidgets(Bundle savedInstanceState) {
        super.initializeWidgets(savedInstanceState);

        tvName = (TextView) findViewById(R.id.tvMovieName);
        tvRating = (TextView) findViewById(R.id.tvMovieRating);
        ivMovieImage = (ImageView) findViewById(R.id.ivMovieImage);
        tbFavorite = (ToggleButton) findViewById(R.id.tbFavorite);
        tbFavorite.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movieId != -1 && mPresenter != null) {
            mPresenter.startPresenterView();
        }
    }

    @Override
    public void initializeArgumentsFromIntent() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_PARAM_MOVIE_ID)){
            movieId = getIntent().getExtras().getLong(EXTRA_PARAM_MOVIE_ID);
            mPresenter.startPresenterView();
        }else
            throw new NullPointerException("Não foi passado id do filme");
    }

    @Override
    public Long getMovieId() {
        return movieId;
    }

    @Override
    public void bindMovieViewData(Movie movie) {
        this.movie = movie;
        tvName.setText(movie.getTitle());
        tvRating.setText(movie.getRating() + "/" + getString(R.string.max_rating));
        ivMovieImage.setImageURI(Uri.parse(movie.getThumbnailPath()));
        tbFavorite.setChecked(movie.isFavorite());
    }

    @Override
    public void setFavOn() {
        tbFavorite.setChecked(true);
    }

    @Override
    public void setFavOff() {
        tbFavorite.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tbFavorite:
                mPresenter.markAsFavorite(this.movie);
                break;
        }
    }
}
