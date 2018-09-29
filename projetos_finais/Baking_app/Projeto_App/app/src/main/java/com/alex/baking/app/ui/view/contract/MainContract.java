package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Recipe;

public interface MainContract {

	interface Presenter extends BaseListContract.Presenter<Recipe> {
		// Ignorado
	}

	interface View extends BaseListContract.View<Recipe> {
		// Ignorado
	}
}
