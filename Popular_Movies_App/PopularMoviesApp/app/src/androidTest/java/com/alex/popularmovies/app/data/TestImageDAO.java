package com.alex.popularmovies.app.data;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.alex.popularmovies.app.dao.MoviesContract;

import java.util.List;

/**
 * Created by Alex on 11/12/2016.
 */

public class TestImageDAO extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    private ImageDAO imageDAO;

    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
        imageDAO = new ImageDAO(mContext);
    }

    public void testInsert() throws Throwable {
        ContentValues fakeImageValues = TestUtilities.createFakeImageValues();
        Image image = new Image(fakeImageValues);
        image = imageDAO.saveOrUpdate(image);
        assertNotSame("Não foi feito add de id para novo obj", image.getId(), -1);
        image = imageDAO.get(MoviesContract.MovieEntry._ID, String.valueOf(image.getId()));
        assertNotSame("Não foi encontrado recem obj", image.getId(), -1);
    }

    public void testSearch() throws Exception {
        int times = 10;
        do{
            TestUtilities.insertImageValues(mContext);
            times --;
        }while (times > 0);

        List<Image> images = imageDAO.search(MoviesContract.ImageEntry.COLUMN_FILE_PATH, "teste.png", null, null);
        assertNotNull(images);
        assertFalse(images.isEmpty());
        assertEquals(images.size(), 10);
    }

    public void testUpdate() throws Throwable {
        long idImage = TestUtilities.insertImageValues(mContext);
        Image image = imageDAO.get(MoviesContract.ImageEntry._ID, String.valueOf(idImage));
        image.setFilePath("Novo path");
        imageDAO.saveOrUpdate(image);
        Image image2 = imageDAO.get(MoviesContract.MovieEntry._ID, String.valueOf(image.getId()));
        assertNotSame("Path sao os mesmos", image.getFilePath(), image2.getFilePath());
    }

    public void testDelete() throws Exception {
        int times = 10;
        do{
            long idImage = TestUtilities.insertImageValues(mContext);
            times --;
        }while (times > 0);

        List<Image> images = imageDAO.search(MoviesContract.ImageEntry.COLUMN_FILE_PATH, "teste.png", null, null);
        assertNotNull(images);
        assertEquals(images.size(), 10);
        Image image = images.get(2);
        imageDAO.delete(image);
        images = imageDAO.search(MoviesContract.ImageEntry.COLUMN_FILE_PATH, "teste.png", null, null);
        assertNotNull(images);
        assertNotSame(images.size(), 10);
        assertEquals(images.size(), 9);
    }
}
