package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;

public interface RecipeContract {

	interface View extends BasePresenter.View<Recipe> {
		// ignorado
	}

	interface Presenter extends IPresenter {
		// ignorado
	}
}
