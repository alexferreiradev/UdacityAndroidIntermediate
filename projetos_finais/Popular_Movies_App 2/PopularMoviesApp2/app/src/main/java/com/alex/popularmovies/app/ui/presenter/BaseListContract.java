package com.alex.popularmovies.app.ui.presenter;

import android.widget.AbsListView;
import android.widget.ListAdapter;
import com.alex.popularmovies.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 18/03/2017.
 */

public interface BaseListContract {

	interface View<ModelType extends BaseModel>
			extends BasePresenter.View<ModelType>, AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

		void createListAdapter(List<ModelType> results);

		void addAdapterData(List<ModelType> result);

		void removeAdapterData(List<ModelType> result);

		ListAdapter getAdapter();

		void destroyListAdapter();

		void showAddOrEditDataView(ModelType data);

		void showDataView(ModelType data);

		void setEmptyView(String text);

		void setGridScroolPosition(int position);

		int getFirstVisibleItemPosition();

	}

	interface Presenter<ModelType extends BaseModel> extends IPresenter {

		void loadMoreData(int firstVisibleItem, int visibleItemCount, int adapterTotalItems);

		void applyFilter(String filterKey, String filterValue);

		void selectItemClicked(ModelType item, int pos);

	}
}
