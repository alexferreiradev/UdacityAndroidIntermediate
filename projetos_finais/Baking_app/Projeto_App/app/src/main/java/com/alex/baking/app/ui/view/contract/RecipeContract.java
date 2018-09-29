package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;

import java.util.List;

public interface RecipeContract {

	interface View extends BasePresenter.View {

		boolean isDualPanel();

		void selectStepItem(Long selectedStepId, int position);

		void setEmptyStepSelectVisibility(boolean visible);
	}

	interface FragmentView {

		void setPresenter(Presenter presenter);

		void bindViewModel(Recipe recipe);

		void addIngredientToAdapter(List<Ingredient> ingredientList);

		void addStepToAdapter(List<Step> stepList);
	}

	interface Presenter extends IPresenter {

		void setRecipeId(Long recipeId);

		void selectStep(Long selectedStepId, int position);

		void setFragmentView(FragmentView fragmentView);
	}
}
