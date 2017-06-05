package com.alex.popularmovies.app.data.repository;

import android.content.ContentValues;
import android.content.Context;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.cache.BaseCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */
public class MovieRepositoryTest {
    @Mock
    BaseCache<Movie> mCacheSource;
    @Mock
    DefaultSource<Movie> mLocalSource;
    @Mock
    DefaultSource<Movie> mRemoteSource;
    @Mock
    Context mContext;
    @InjectMocks
    MovieRepository movieRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieRepository = new MovieRepository(mContext);
    }

    @Test
    public void testInsert() throws Exception {
        long result = movieRepository.insert(null, new ContentValues(0));
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testDelete() throws Exception {
        int result = movieRepository.delete(null, "selection", new String[]{"selectionArgs"});
        Assert.assertEquals(0, result);
    }

    @Test
    public void testUpdate() throws Exception {
        int result = movieRepository.update(null, new ContentValues(0), "selection", new String[]{"selectionArgs"});
        Assert.assertEquals(0, result);
    }

    @Test
    public void testQuery() throws Exception {
        List<Movie> result = movieRepository.query(null, new String[]{"projection"}, "selection", new String[]{"selectionArgs"}, "sortOrder");
        Assert.assertEquals(Arrays.<Movie>asList(new Movie()), result);
    }

    @Test
    public void testCreateCache() throws Exception {
        movieRepository.createCache(Arrays.<Movie>asList(new Movie()));
        Assert.assertNotNull(mCacheSource);
    }

    @Test
    public void testDestroyCache() throws Exception {
        movieRepository.destroyCache(Arrays.<Movie>asList(new Movie()));
        Assert.assertNull(movieRepository.mCacheSource);
    }

    @Test
    public void testUpdateCache() throws Exception {
        movieRepository.updateCache(Arrays.<Movie>asList(new Movie()));
        Assert.assertTrue(movieRepository.mCacheSource.getDirty());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme