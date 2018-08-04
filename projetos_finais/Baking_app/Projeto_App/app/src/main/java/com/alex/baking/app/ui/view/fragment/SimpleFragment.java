package com.alex.baking.app.ui.view.fragment;

public interface SimpleFragment<ModelType, PresenterType> {

	void startView(ModelType model) throws IllegalArgumentException;

	ModelType destroyView(ModelType model);

	void setPresenter(PresenterType presenter);

	/**
	 * Mostra uma barra de progresso
	 *
	 * @param progress -1 indica que é indeterminado, caso contrario, atualiza a barra para o progress.
	 */
	void showProgressBar(int progress);

	void hideProgressBar();

}
