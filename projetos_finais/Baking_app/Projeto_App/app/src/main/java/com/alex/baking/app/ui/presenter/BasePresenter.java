package com.alex.baking.app.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.repository.DefaultRepository;

/**
 * Created by Alex on 16/03/2017.
 */

public abstract class BasePresenter<ViewType extends BasePresenter.View,
		ModelType extends BaseModel,
		RepoType extends DefaultRepository,
		TaskParamType, TaskProgressType, TaskResultType> implements IPresenter {

	protected Context mContext;
	protected ViewType mView;
	protected RepoType mRepository;

	protected BasePresenter(ViewType mView, Context mContext, Bundle savedInstanceState, RepoType mRepository) {
		this.mContext = mContext;
		this.mView = mView;
		this.mRepository = mRepository;
		initializeWidgets(savedInstanceState);
	}

	/**
	 * Deve ser chamado depois de instanciar o presenter.
	 * <p>
	 * Realiza funções necessárias para view iniciar: carrega argumentos, carrega lista.
	 */
	public void startPresenterView() {
		mView.initializeArgumentsFromIntent();
		initialize();
	}

	protected abstract void initialize();

	protected void hideProgressView() {
		mView.setLoadProgressBarVisibility(false);
	}

	protected void showProgressView() {
		mView.setLoadProgressBarVisibility(true);
	}

	/**
	 * Chamado para fazer bind entre view e atributos da activity.
	 *
	 * @param savedInstanceState -
	 */
	protected void initializeWidgets(Bundle savedInstanceState) {
		mView.initializeWidgets(savedInstanceState);
	}

	protected void backgroudFinished(@NonNull TaskResultType taskResultType) {
	}

	protected TaskResultType loadInBackgroud(TaskParamType[] taskParamTypes) {
		return null;
	}

	@SuppressLint("StaticFieldLeak")
	class BackgroundTask extends AsyncTask<TaskParamType, TaskProgressType, TaskResultType> {

		@SafeVarargs
		@Override
		protected final TaskResultType doInBackground(TaskParamType... taskParamTypes) {
			return loadInBackgroud(taskParamTypes);
		}

		@Override
		protected void onPostExecute(TaskResultType taskResultType) {
			super.onPostExecute(taskResultType);
			if (taskResultType == null) {
				mView.showErrorMsg("Erro interno");
				return;
			}
			backgroudFinished(taskResultType);
		}
	}

	public interface View {
		/**
		 * Inverte o atributo visible de um progressBar
		 */
		void setLoadProgressBarVisibility(boolean toVisible);

		/**
		 * Faz bind entre view e atributos da activity.
		 * <p>
		 * O presenter neste momento ainda não foi instanciado.
		 *
		 * @param savedInstanceState - dados da instancia salvos
		 */
		void initializeWidgets(Bundle savedInstanceState);

		void initializeArgumentsFromIntent();

		void showErrorMsg(String msg);

		void showSuccessMsg(String msg);

		void setActionBarTitle(String title);
	}
}
