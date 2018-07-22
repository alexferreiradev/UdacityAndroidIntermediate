package com.alex.baking.app.data.repository.movie;

import com.alex.baking.app.data.exception.DataException;
import com.alex.baking.app.data.model.Movie;
import com.alex.baking.app.data.model.Review;
import com.alex.baking.app.data.model.Video;
import com.alex.baking.app.data.repository.DefaultRepository;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MovieRepositoryContract extends DefaultRepository<Movie> {

	List<Movie> moviesByPopularity(int limit, int offset) throws DataException;

	List<Movie> moviesByTopRate(int limit, int offset) throws DataException;

	Movie updateMovieFavoriteStatus(Movie movie) throws DataException;

	List<Movie> favoriteMovieList(int limit, int offset, MovieRepository.MovieFilter filter) throws DataException;

	List<Review> reviewListByMovie(Long movieId, int limit, int offset) throws DataException;

	List<Video> videoListByMovie(Long movieId, int limit, int offset) throws DataException;

	boolean hasCache() throws DataException;

	List<Movie> getCurrentCache() throws DataException;

}
