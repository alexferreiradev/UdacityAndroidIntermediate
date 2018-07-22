package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

/**
 * Created by Alex on 17/03/2017.
 */

/**
 * Classe base para criar presenter para activities que tenham listview ou algum container de info.
 *
 * @param <ViewType>  - Interface
 * @param <ModelType> - Tipo de model
 */
public abstract class BaseListPresenter<ViewType extends BaseListContract.View, ModelType extends BaseModel, RepoType extends DefaultRepository>
		extends BasePresenter<ViewType, ModelType, RepoType>
		implements BaseListContract.Presenter<ModelType> {

	// Esta configuração deve ser dinamica quando trabalhado com outros tamanhos de dispositivos
	private static final int LIMIT_INITIAL = 80; // 30 pronto para mostrar + "total a carregar" esperando no adapter
	private static final int INTERVAL_TO_LOAD_MORE = 50; // 30 + este valor = total no adapter
	private static final int TOTAL_FILTER_FROM_ADAPTER = 1000; // Valor que uma CPU básica não irá demorar para filtrar
	private static final String TAG = BaseListPresenter.class.getSimpleName();

	protected int mLoadItemsLimit;
	protected int mOffset;
	protected String mFilterKey;
	protected String mFilterValue;
	protected int lastOffsetSolicited;
	protected int lastPositionInScrool;

	public BaseListPresenter(ViewType mView, Context mContext, Bundle savedInstanceState, RepoType mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
		resetPaginationCounter();
	}

	/**
	 * Devem ser implementados
	 */
	protected abstract void setEmptyView();

	protected abstract void loadDataFromSource();

	/**
	 * Crud Funcoes
	 */
	protected void updateDataInSource(ModelType data) {
	}

	protected void removeDataInSource(ModelType data) {
	}

	protected void saveDataInSource(ModelType data) {
	}

	/**
	 * Filtros
	 */
	protected void applyFilterFromAdapter() {
	}

	protected void applyFilterFromSource() {
	}

	@Override
	public void applyFilter(String filterKey, String filterValue) {
		this.mFilterKey = filterKey;
		this.mFilterValue = filterValue;
		if (!isNewAdapter())
			applyFilterFromAdapter();
	}

	/**
	 * Verifica se precisa de carregar mais itens ao usuário fazer scroll na lista de itens. São feitas
	 * duas verificacoes: 1- se precisa de carregar mais (ultimo visivel perto de total carregado);
	 * 2 - se não foi feito uma requisicao de carregamento antes (offset > total carregado);
	 * <p>
	 * Deve alterar o mLimit de acordo com o total de linhas visiveis e o offset com adição do mLimit. Uma
	 * alteração para considerar diferentes tipos de dispositivos.
	 *
	 * @param firstVisibleItem  - posição do primeiro item visível
	 * @param visibleItemCount  - total de linhas que são visíveis
	 * @param adapterTotalItems - total de itens no adapter
	 */
	@Override
	public synchronized void loadMoreData(int firstVisibleItem, int visibleItemCount, int adapterTotalItems) {
		int lastItemVisiblePosition = firstVisibleItem + visibleItemCount;
		if (mLoadItemsLimit < visibleItemCount + INTERVAL_TO_LOAD_MORE) {
			mLoadItemsLimit = visibleItemCount + INTERVAL_TO_LOAD_MORE;
		}

		Log.v(TAG, "Load more com: total items carregados: " + adapterTotalItems + " lastVisi: " + lastItemVisiblePosition);

		if (lastItemVisiblePosition > adapterTotalItems - INTERVAL_TO_LOAD_MORE) {
			Log.v(TAG, "Load more com LastOffset: " + lastOffsetSolicited + " e offset: " + mOffset);
			if (mOffset > lastOffsetSolicited) {
				lastOffsetSolicited = mOffset;
				Log.v(TAG, "Load more solicitando mais dados");
				loadDataFromSource();
			}
		}
	}

	protected void loadUntilLastPosition() {
		ListAdapter adapter = mView.getAdapter();
		boolean addMoreToAdapter = adapter != null && ((adapter.getCount() - INTERVAL_TO_LOAD_MORE) <= lastPositionInScrool);
		boolean moreToAdapter = adapter != null && adapter.getCount() > lastPositionInScrool;
		if (hasLastPositionValid() && addMoreToAdapter) {
			Log.d(TAG, "Carregando mais até adapter > lastpos. Adapter: " + adapter.getCount() + " x lastPos: " + lastPositionInScrool);
			mView.setGridScroolPosition(adapter.getCount());
		} else if (hasLastPositionValid() && mView.getFirstVisibleItemPosition() != lastPositionInScrool) {
			Log.d(TAG, "Carregando mais até visible = lastPos. " + mView.getFirstVisibleItemPosition() + " " + lastPositionInScrool);
			mView.setGridScroolPosition(lastPositionInScrool);
			Log.d(TAG, "Invalidando lastPos");
			setLastPositionInvalid();
		}
	}

	private boolean hasLastPositionValid() {
		return lastPositionInScrool >= 0;
	}

	private void setLastPositionInvalid() {
		lastPositionInScrool = -1;
	}

	protected void reCreateAdapter() {
		Log.d(TAG, "Recriando adapter.");
		mRepository.setCacheToDirty();
		mView.destroyListAdapter();

		setLastPositionInvalid();
		resetPaginationCounter();
		initialize();
	}

	@Override
	protected void initialize() {
		if (isNewAdapter()) {
			loadMoreData(0, 0, 0);
		} else {
			loadMoreData(0, 0, mView.getAdapter().getCount());
		}
	}

	@Override
	protected void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		setEmptyView();
	}

	protected boolean isNewAdapter() {
		return mView.getAdapter() == null || mView.getAdapter().isEmpty();
	}

	private void resetPaginationCounter() {
		mLoadItemsLimit = LIMIT_INITIAL;
		mOffset = 0;
		lastOffsetSolicited = -1;
	}
}
