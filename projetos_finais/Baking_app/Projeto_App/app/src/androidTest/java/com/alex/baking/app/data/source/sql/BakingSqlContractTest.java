package com.alex.baking.app.data.source.sql;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) // Necessita de Uri do Android
public class BakingSqlContractTest {

	@Test
	public void test_create_table_sql() {
		String createRecipeSql = BakingContract.RecipeEntry.createTableSql();
		String createIngredientSql = BakingContract.IngredientEntry.createTableSql();
		String createStepSql = BakingContract.StepEntry.createTableSql();

		assertEquals("create table recipe(_id integer primary key autoincrement, id_from_api integer unique, nome text, serving text, image text)", createRecipeSql);
		assertEquals("create table ingredient(_id integer primary key autoincrement, recipe_id integer, quantity real, measure text, ingredient text)", createIngredientSql);
		assertEquals("create table step(_id integer primary key autoincrement, id_from_api integer unique, recipe_id integer, short_description real, description text, video_url text, thumbnail_url text)", createStepSql);
	}

	@Test
	public void test_drop_table_sql() {
		String dropRecipeSql = BakingContract.RecipeEntry.dropTableSql();
		String dropIngredientSql = BakingContract.IngredientEntry.dropTableSql();
		String dropStepSql = BakingContract.StepEntry.dropTableSql();

		assertEquals("drop table if exists recipe", dropRecipeSql);
		assertEquals("drop table if exists ingredient", dropIngredientSql);
		assertEquals("drop table if exists step", dropStepSql);
	}

}