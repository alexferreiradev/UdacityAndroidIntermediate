package com.alex.baking.app.ui.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.ui.view.contract.MainContract;

import java.util.List;

public class MainActivity extends BaseActivity<Recipe, MainContract.View, MainContract.Presenter> implements MainContract.View {

	@BindView(R.id.rvRecipeList)
	private RecyclerView recipeRV;
	@BindView(R.id.tvEmpty)
	private TextView emptyTV;

	public MainActivity() {
		super("Baking Time");
	}

	public MainActivity(String mTitle) {
		super(mTitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		ButterKnife.bind(this);
	}

	@Override
	public void initializeArgumentsFromIntent() {
	}

	@Override
	public void createListAdapter(List<Recipe> results) {
	}

	@Override
	public void addAdapterData(List<Recipe> result) {

	}

	@Override
	public void removeAdapterData(List<Recipe> result) {

	}

	@Override
	public ListAdapter getAdapter() {
		return null;
	}

	@Override
	public void destroyListAdapter() {

	}

	@Override
	public void showAddOrEditDataView(Recipe data) {

	}

	@Override
	public void showDataView(Recipe data) {

	}

	@Override
	public void setEmptyView(String text) {

	}

	@Override
	public void setGridScroolPosition(int position) {

	}

	@Override
	public int getFirstVisibleItemPosition() {
		return 0;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
