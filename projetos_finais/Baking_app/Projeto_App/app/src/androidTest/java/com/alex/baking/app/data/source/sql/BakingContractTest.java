package com.alex.baking.app.data.source.sql;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) // Necessita de Uri
public class BakingContractTest {

	@Test
	public void test_create_movie_table_sql() {
		String createSql = BakingContract.RecipeEntry.createTableSql();

		assertEquals("create table movie(_id integer primary key autoincrement, " +
				"id_from_api integer unique, " +
				"title text, poster_path text, " +
				"thumbnail_path text, " +
				"synopsys text, rating real, " +
				"release_date integer, " +
				"is_favorite bool)", createSql);
	}

	@Test
	public void test_drop_movie_table_sql() {
		String dropSql = BakingContract.RecipeEntry.dropTableSql();

		assertEquals("drop table if exists movie", dropSql);
	}

}