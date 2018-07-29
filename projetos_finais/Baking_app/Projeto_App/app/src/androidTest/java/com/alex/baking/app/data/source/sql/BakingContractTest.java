package com.alex.baking.app.data.source.sql;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) // Necessita de Uri do Android
public class BakingContractTest {

	@Test
	public void test_create_movie_table_sql() {
		String createSql = BakingContract.RecipeEntry.createTableSql();

		assertEquals("create table recipe(" +
				"_id integer primary key autoincrement, " +
				"id_from_api integer unique, " +
				"nome text, " +
				"serving text, " +
				"image text, )", createSql);
	}

	@Test
	public void test_drop_movie_table_sql() {
		String dropSql = BakingContract.RecipeEntry.dropTableSql();

		assertEquals("drop table if exists recipe", dropSql);
	}

}