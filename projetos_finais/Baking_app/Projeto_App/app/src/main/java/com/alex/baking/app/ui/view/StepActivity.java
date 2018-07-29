package com.alex.baking.app.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.view.contract.StepContract;

import java.util.List;

public class StepActivity extends BaseActivity<Step, StepContract.View, StepContract.Presenter> implements StepContract.View {

	public StepActivity() {
		super("");
		setTitle(getString(R.string.recipe_steps_label));
	}

	public StepActivity(String mTitle) {
		super(mTitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);
	}

	@Override
	public void createListAdapter(List<Step> results) {

	}

	@Override
	public void addAdapterData(List<Step> result) {

	}

	@Override
	public void removeAdapterData(List<Step> result) {

	}

	@Override
	public ListAdapter getAdapter() {
		return null;
	}

	@Override
	public void destroyListAdapter() {

	}

	@Override
	public void showAddOrEditDataView(Step data) {

	}

	@Override
	public void showDataView(Step data) {

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

	@Override
	public void initializeArgumentsFromIntent() {

	}
}
