package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

import java.util.List;

/**
 * Created by Alex on 17/03/2017.
 */

/**
 * Classe base para criar presenter para activities que tenham listview ou algum container de info.
 * @param <ViewType> - Interface
 * @param <ModelType> - Tipo de model
 */
public abstract class BaseListPresenter<ViewType extends BaseListContract.View, ModelType extends BaseModel, RepoType extends DefaultRepository>
        extends BasePresenter<ViewType, ModelType, RepoType>
        implements BaseListContract.Presenter<ModelType>{

    private static final int LIMIT_INITIAL = 80; // 30 pronto para mostrar + 20 esperando no adapter
    public static final int INTERVAL_TO_LOAD_MORE = 50; // 30 já visto, 20 faltando para visualizar
    private static final int TOTAL_FILTER_FROM_ADAPTER = 1000; // Valor que uma CPU básica não irá demorar para filtrar

    protected int mLoadItemsLimit;
    protected int mOffset;
    protected String mFilterKey;
    protected String mFilterValue;

    public BaseListPresenter(ViewType mView, Context mContext, Bundle savedInstanceState, RepoType mRepository) {
        super(mView, mContext, savedInstanceState, mRepository);
    }

    /** Devem ser implementados */
    protected abstract void setEmptyView();
    protected abstract void loadDataFromSource();

    /** Crud Funcoes */
    protected void updateDataInSource(ModelType data){}
    protected void removeDataInSource(ModelType data){}
    protected void saveDataInSource(ModelType data){}

    /** Filtros */
    protected void applyFilterFromAdapter(){}
    protected void applyFilterFromSource(){}

    public void selectItemClicked(ModelType item){}
    public void showAddOrEditView(ModelType data){}

    @Override
    public void applyFilter(String filterKey, String filterValue) {
        this.mFilterKey = filterKey;
        this.mFilterValue = filterValue;
        if (!isNewAdapter())
            applyFilterFromAdapter();
    }

    @Override
    public void populateAdapter(List<ModelType> result){
        if (result != null && !result.isEmpty() && isNewAdapter()){
            mView.createListAdapter(result);
        }else if (result != null){
            mView.addAdapterData(result);
        }
    }

    /**
     * Verifica se precisa de carregar mais itens ao usuário fazer scroll na lista de itens. São feitas
     * duas verificacoes: 1- se precisa de carregar mais (ultimo visivel perto de total carregado);
     * 2 - se não foi feito uma requisicao de carregamento antes (offset > total carregado);
     *
     * Deve alterar o mLimit de acordo com o total de linhas visiveis e o offset com adição do mLimit. Uma
     * alteração para considerar diferentes tipos de dispositivos.
     *
     * @param firstVisibleItem - posição do primeiro item visível
     * @param visibleItemCount - total de linhas que são visíveis
     * @param adapterTotalItems - total de itens no adapter
     */
    @Override
    public synchronized void loadMoreData(int firstVisibleItem, int visibleItemCount, int adapterTotalItems){
        int lastItemVisiblePosition = firstVisibleItem + visibleItemCount;
        Log.d("teste", "total items carregados: " + adapterTotalItems + "lastVisi: " + lastItemVisiblePosition);
        if (lastItemVisiblePosition > adapterTotalItems - INTERVAL_TO_LOAD_MORE){
            loadDataFromSource();
        }

        if (mLoadItemsLimit < visibleItemCount + INTERVAL_TO_LOAD_MORE) {
            mLoadItemsLimit = visibleItemCount + INTERVAL_TO_LOAD_MORE;
        }
    }

    @Override
    public void reCreateAdapter(){
        mView.destroyListAdapter();
        initialize();
    }

    @Override
    protected void initialize() {
        mView.destroyListAdapter();
        resetPaginationCounter();
        loadMoreData(0, 0, 0);
    }

    @Override
    protected void initializeWidgets(Bundle savedInstanceState) {
        super.initializeWidgets(savedInstanceState);
        setEmptyView();
    }

    protected boolean isNewAdapter() {
        return mView.getAdapter() == null || mView.getAdapter().isEmpty();
    }

    private void resetPaginationCounter(){
        mLoadItemsLimit = LIMIT_INITIAL;
        mOffset = 0;
    }
}
