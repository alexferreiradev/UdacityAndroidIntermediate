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
import com.alex.baking.app.ui.adapter.RecipeAdapter;
import com.alex.baking.app.ui.view.contract.MainContract;

import java.util.List;

public class MainActivity extends BaseActivity<Recipe, MainContract.View, MainContract.Presenter> implements MainContract.View {

	@BindView(R.id.rvRecipeList)
	RecyclerView recipeRV;
	@BindView(R.id.tvEmpty)
	TextView emptyTV;
	private RecipeAdapter recipeAdapter;

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
		// nao utilizado
	}

	@Override
	public void createListAdapter(List<Recipe> results) {
		recipeAdapter = new RecipeAdapter(results, mPresenter, this);
		recipeRV.setAdapter(recipeAdapter);
	}

	@Override
	public void addAdapterData(List<Recipe> result) {
		recipeAdapter.addAllModel(result);
	}

	@Override
	public void removeAdapterData(List<Recipe> result) {
		recipeAdapter.removeAllModel(result);
	}

	@Override
	public ListAdapter getAdapter() {
		return null;
	}

	@Override
	public RecyclerView.Adapter getRecycleAdapter() {
		return recipeAdapter;
	}

	@Override
	public void destroyListAdapter() {
		recipeAdapter = null;
	}

	@Override
	public void showAddOrEditDataView(Recipe data) {
		// não utilizado
	}

	@Override
	public void showDataView(Recipe data) {
		// não utilizado
	}

	@Override
	public void setEmptyView(String text) {
		emptyTV.setText(text);
	}

	@Override
	public void setGridScroolPosition(int position) {
		recipeRV.smoothScrollToPosition(position);
	}

	@Override
	public int getFirstVisibleItemPosition() {
		return 0;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nao utilizado
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// nao utilizado
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mPresenter.selectItemClicked((Recipe) parent.getAdapter().getItem(position), position);
	}
}
