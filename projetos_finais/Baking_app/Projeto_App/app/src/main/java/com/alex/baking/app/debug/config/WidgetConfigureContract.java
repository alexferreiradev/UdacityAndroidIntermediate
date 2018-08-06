package com.alex.baking.app.debug.config;

import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;

import java.util.List;

public interface WidgetConfigureContract {

	interface View extends BasePresenter.View {

		void addSpinnerData(List<Recipe> data);
	}

	interface Presenter extends IPresenter {

		void selectRecipe(Recipe recipe);

		void setWidgetId(int widgetId);
	}
}
