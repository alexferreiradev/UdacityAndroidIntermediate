package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

/**
 * Created by Alex on 16/03/2017.
 */

public abstract class BasePresenter<ViewType extends BasePresenter.View,
        ModelType extends BaseModel,
        RepoType extends DefaultRepository> implements IPresenter {

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

    public interface View<ModelType extends BaseModel> {
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
    }
}
