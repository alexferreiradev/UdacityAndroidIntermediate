package com.alex.baking.app.data.repository.recipe;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.DefaultRepository;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface RecipeRepositoryContract extends DefaultRepository<Recipe> {

	List<Recipe> getRecipeList(int limit, int offset) throws ConnectionException;

	List<Ingredient> getIngredientListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException;

	List<Step> getStepListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException;

	Step recoverStep(Long stepId) throws ConnectionException;
}
