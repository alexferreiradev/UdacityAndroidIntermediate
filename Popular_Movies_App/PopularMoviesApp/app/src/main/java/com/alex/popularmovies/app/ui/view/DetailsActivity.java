package com.alex.popularmovies.app.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.MovieRepository;
import com.alex.popularmovies.app.ui.presenter.DetailPresenter;
import com.alex.popularmovies.app.util.MovieImageUtil;

public class DetailsActivity extends BaseActivity<Movie, DetailPresenter.View, DetailPresenter> implements DetailPresenter.View {

    public static final String EXTRA_PARAM_MOVIE_ID = "Movie id";
    private static final String TAG = DetailsActivity.class.getSimpleName();
    private TextView tvName;
    private TextView tvRating;
    private ImageView ivMovieImage;
    private Movie mMovie;
    private long movieId = -1;
    private ToggleButton tbFavorite;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mPresenter = new DetailPresenter(this, this, savedInstanceState, MovieRepository.getInstance(this));
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
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Não foi passado id do filme");
        if (getIntent() != null && getIntent().hasExtra(EXTRA_PARAM_MOVIE_ID)) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                throw illegalArgumentException;
            }

            movieId = extras.getLong(EXTRA_PARAM_MOVIE_ID);
            Log.d(TAG, "Argumento filme id: " + movieId);
            if (movieId < 0) {
                throw illegalArgumentException;
            }
        } else {
            throw illegalArgumentException;
        }
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
        MovieImageUtil.setImageViewWithPicasso(ivMovieImage, this, movie, MovieImageUtil.IMAGE_LENGTH_W_92);
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
    public void closeAndShowMovieGrid() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbFavorite:
                mPresenter.markAsFavorite(this.movie);
                break;
        }
    }
}
