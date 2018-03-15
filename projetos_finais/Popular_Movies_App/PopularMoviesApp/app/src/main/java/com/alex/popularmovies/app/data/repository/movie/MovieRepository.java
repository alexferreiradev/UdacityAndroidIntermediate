package com.alex.popularmovies.app.data.repository.movie;

import android.util.Log;

import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.BaseRepository;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.remote.MoviesRemoteQuery;
import com.alex.popularmovies.app.data.source.queryspec.remote.RemoteQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieRepository extends BaseRepository<Movie> implements MovieRepositoryContract {
    private static final String TAG = MovieRepository.class.getSimpleName();

    public MovieRepository(BaseCache<Movie> mCacheSource, DefaultSource<Movie> mLocalSource, DefaultSource<Movie> mRemoteSource) {
        super(mCacheSource, mLocalSource, mRemoteSource);
    }

    @Override
    protected void createCache(List<Movie> data) {
        updateCache(data, 0);
    }

    @Override
    protected void destroyCache(List<Movie> data) {
        mCacheSource.setDirty(true);
    }

    @Override
    protected void updateCache(List<Movie> data, int offset) {
        if (mCacheSource.isNewCache() || offset == 0) {
            mCacheSource.updateCacheTo(data);
        } else {
            mCacheSource.addAllCache(data, offset);
        }
    }

    @Override
    public Movie create(Movie model) throws DataException {
        throw new DataException("Metodo não permitido para esta versao");
    }

    @Override
    public Movie recover(Long id) throws DataException {
        if (id == null || id < 0) {
            return null;
        }

        try {
            Movie movie = mCacheSource.recover(id);
            if (movie == null) {
                Log.d(TAG, "Não foi encontrado o filme no cache, marcando o cache como desatualizado");
                mCacheSource.setDirty(true);
            }

            if (mCacheSource.isDirty()) {
                Log.d(TAG, "Buscando filme em fonte remota.");
                movie = mRemoteSource.recover(id);
                if (movie == null) {
                    Log.w(TAG, "Filme com id: " + id + " não existe em nenhuma fonte disponível.");
                }
            }

            return movie;
        } catch (SourceException e) {
            Log.e(TAG, "Erro ao tentar buscar no cache o id.", e);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Erro desconhecido: ", e);
            throw new DataException("Erro desconhecido", e);
        }
    }

    @Override
    public Movie update(Movie model) throws DataException {
        throw new DataException("Metodo não permitido para esta versao");
    }

    @Override
    public Movie delete(Movie model) throws DataException {
        throw new DataException("Metodo não permitido para esta versao");
    }

    @Override
    public List<Movie> moviesByPopularity(int limit, int offset) throws DataException {
        RemoteQuery querySpec = new MoviesRemoteQuery(limit, offset, "popular");
        return getMoviesBySpec(querySpec);
    }

    @Override
    public List<Movie> moviesByTopRate(int limit, int offset) throws DataException {
        RemoteQuery querySpec = new MoviesRemoteQuery(limit, offset, "top_rated");
        return getMoviesBySpec(querySpec);
    }

    @Override
    public boolean hasCache() throws DataException {
        return !mCacheSource.isDirty();
    }

    @Override
    public List<Movie> getCurrentCache() throws DataException {
        return mCacheSource.isDirty() ? new ArrayList<Movie>() : mCacheSource.getCache();
    }

    private List<Movie> getMoviesBySpec(RemoteQuery remoteQuery) throws DataException {
        List<Movie> movies = new ArrayList<>();

        try {
            movies = mRemoteSource.recover(remoteQuery);
            updateCache(movies, remoteQuery.getOffset());
        } catch (SourceException e) {
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Erro desconhecido: ", e);
            throw new DataException("Erro desconhecido: ", e);
        }

        return movies;
    }

    @Override
    public void setCacheToDirty() {
        mCacheSource.setDirty(true);
    }
}
