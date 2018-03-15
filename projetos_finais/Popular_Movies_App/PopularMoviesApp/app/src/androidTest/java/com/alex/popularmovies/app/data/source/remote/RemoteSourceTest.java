package com.alex.popularmovies.app.data.source.remote;

import android.support.test.runner.AndroidJUnit4;

import com.alex.popularmovies.app.data.model.Movie;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Alex on 03/09/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RemoteSourceTest {

    private RemoteMovie remoteMovie;

    @Ignore
    @Test
    public void query() throws Exception {

    }

    @Test
    public void list_all_movies() throws Exception {
        remoteMovie = new RemoteMovie(null);

        List<Movie> list = remoteMovie.list(null);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(20, list.size()); // page size = 20
    }

}