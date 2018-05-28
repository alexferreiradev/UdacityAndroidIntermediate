package com.alex.popularmovies.app.ui.presenter;

import android.widget.AbsListView;
import android.widget.ListAdapter;
import com.alex.popularmovies.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 18/03/2017.
 */

public interface BaseListContract {

	public interface View<ModelType extends BaseModel>
			extends BasePresenter.View<ModelType>, AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

		public void createListAdapter(List<ModelType> results);

		public void addAdapterData(List<ModelType> result);

		public void removeAdapterData(List<ModelType> result);

		public ListAdapter getAdapter();

		public void destroyListAdapter();

		public void showAddOrEditDataView(ModelType data);

		public void showDataView(ModelType data);

		public void setEmptyView(String text);

	}

	public interface Presenter<ModelType extends BaseModel> extends IPresenter {

		void loadMoreData(int firstVisibleItem, int visibleItemCount, int adapterTotalItems);

		void applyFilter(String filterKey, String filterValue);

		void selectItemClicked(ModelType item, int pos);

	}
}
