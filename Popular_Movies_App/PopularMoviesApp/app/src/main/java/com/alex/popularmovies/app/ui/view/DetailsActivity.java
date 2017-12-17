package com.alex.popularmovies.app.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.movie.MovieRepository;
import com.alex.popularmovies.app.data.repository.movie.MovieRepositoryContract;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.remote.RemoteMovie;
import com.alex.popularmovies.app.data.source.sql.MovieSql;
import com.alex.popularmovies.app.ui.presenter.detail.DetailContract;
import com.alex.popularmovies.app.ui.presenter.detail.DetailPresenter;
import com.alex.popularmovies.app.util.MovieImageUtil;

import java.text.DecimalFormat;

public class DetailsActivity extends BaseActivity<Movie, DetailPresenter.View, DetailPresenter> implements DetailContract.View {
    public static final String EXTRA_PARAM_MOVIE_ID = "Movie id";
    private static final String TAG = DetailsActivity.class.getSimpleName();
    private TextView tvName;
    private TextView tvYear;
    private TextView tvTime;
    private TextView tvRating;
    private TextView tvSynopsis;
    private ImageView ivMovieImage;
    private ToggleButton tbFavorite;

    private long movieId = -1;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        BaseCache<Movie> movieCache = MovieCache.getInstance();
        MovieRepositoryContract movieRepository = new MovieRepository(movieCache, new MovieSql(this), new RemoteMovie());
        mPresenter = new DetailPresenter(this, this, savedInstanceState, movieRepository);
    }

    @Override
    public void initializeWidgets(Bundle savedInstanceState) {
        super.initializeWidgets(savedInstanceState);

        tvName = findViewById(R.id.tvMovieName);
        tvRating = findViewById(R.id.tvMovieRating);
        ivMovieImage = findViewById(R.id.ivMovieImage);
        tbFavorite = findViewById(R.id.tbFavorite);
        tvYear = findViewById(R.id.tvMovieYear);
        tvSynopsis = findViewById(R.id.tvMovieSynopsis);
        tvTime = findViewById(R.id.tvMovieTime);
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
        this.mMovie = movie;
        Log.d(TAG, "Fazendo bind de filme: " + movie);

        tvName.setText(movie.getTitle());
        String popularityFormated = new DecimalFormat("#.##").format(movie.getPopularity() * 10);
        String ratingFormatted = popularityFormated + "/" + getString(R.string.max_rating);
        tvRating.setText(ratingFormatted);
        MovieImageUtil.setImageViewWithPicasso(ivMovieImage, this, movie, MovieImageUtil.IMAGE_LENGTH_W_185);
        tbFavorite.setChecked(movie.isFavorite());
        tbFavorite.setEnabled(true);
        tvTime.setText("nao encontrado na API");
        tvSynopsis.setText(movie.getSynopsis());
        tvYear.setText("nao encontrado na API");
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

}
