package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

/**
 * Created by Alex on 16/03/2017.
 */

public abstract class BasePresenter<ViewType extends BasePresenter.View,
        ModelType extends BaseModel> {

    protected Context mContext;
    protected ViewType mView;
    protected DefaultRepository mRepository;

    protected BasePresenter(ViewType mView, Context mContext, Bundle savedInstanceState, DefaultRepository mRepository) {
        this.mContext = mContext;
        this.mView = mView;
        this.mRepository = mRepository;
        initializeWidgets(savedInstanceState);
    }

    /**
     * Deve ser chamado depois de instanciar o presenter.
     *
     * Realiza funções necessárias para view iniciar: carrega argumentos, carrega lista.
     */
    public void startPresenterView() {
        mView.initializeArgumentsFromIntent();
        initialize();
    }

    protected abstract void initialize();

    /**
     * Chamado para fazer bind entre view e atributos da activity.
     * @param savedInstanceState -
     */
    protected void initializeWidgets(Bundle savedInstanceState) {
        mView.initializeWidgets(savedInstanceState);
    }

    /**
     * Funções que todas mView tem
     */
    public interface View<ModelType extends BaseModel> {

        /**
         * Inverte o atributo visible de um progressBar
         */
        public void toggleProgressBar();

        /**
         * Faz bind entre view e atributos da activity.
         *
         * O presenter neste momento ainda não foi instanciado.
         *
         * @param savedInstanceState - dados da instancia salvos
         */
        public void initializeWidgets(Bundle savedInstanceState);

        public void initializeArgumentsFromIntent();

        public void showErrorMsg(String msg);

        public void showSuccessMsg(String msg);

    }
}
