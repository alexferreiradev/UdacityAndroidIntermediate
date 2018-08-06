package com.alex.baking.app.data.repository.recipe;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.DefaultRepository;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

import java.net.URL;
import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface RecipeRepositoryContract extends DefaultRepository<Recipe> {

	List<Recipe> getRecipeList(int limit, int offset) throws ConnectionException;

	List<Ingredient> getIngredientListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException;

	List<Step> getStepListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException;

	Step recoverStep(Long stepId) throws ConnectionException;

	void setRemoteIngredientSource(DefaultSource<Ingredient, URL> remoteIngredientSource);

	void setRemoteStepSource(DefaultSource<Step, URL> remoteStepSource);

	void setIngredientLocalSource(DefaultSource<Ingredient, SqlQuery> ingredientLocalSource);
}
