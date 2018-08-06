package com.alex.baking.app.debug.config;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.repository.recipe.RecipeRepository;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.cache.RecipeCache;
import com.alex.baking.app.data.source.remote.RecipeSource;
import com.alex.baking.app.data.source.remote.network.NetworkResourceManager;
import com.alex.baking.app.data.source.sql.RecipeSqlSource;
import com.alex.baking.app.debug.BakingWidget;

import java.util.List;

/**
 * The configuration screen for the {@link BakingWidget BakingWidget} AppWidget.
 */
public class BakingWidgetConfigureActivity extends Activity implements WidgetConfigureContract.View {

	@BindView(R.id.btSelectRecipe)
	Button selectBT;
	@BindView(R.id.spRecipe)
	Spinner recipeSP;

	private WidgetConfigurePresenter presenter;


	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setResult(RESULT_CANCELED); // Caso usuario precionar back

		setContentView(R.layout.baking_widget_configure);
		ButterKnife.bind(this);

		int widgetId = extractWidgetId();
		if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
			return;
		}

		RecipeRepositoryContract repo = new RecipeRepository(
				RecipeCache.getInstance(),
				new RecipeSqlSource(this),
				new RecipeSource(new NetworkResourceManager())
		);
		presenter = new WidgetConfigurePresenter(this, this, icicle, repo);
		presenter.setWidgetId(widgetId);
	}

	private int extractWidgetId() {
		int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		return mAppWidgetId;
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (presenter != null) {
			presenter.startPresenterView();
		}
	}

	@Override
	public void addSpinnerData(List<Recipe> data) {
		SpinnerAdapter adapter = new RecipeSpinnerAdapter(this, data);
		recipeSP.setAdapter(adapter);
	}

	@Override
	public void setLoadProgressBarVisibility(boolean toVisible) {
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		selectBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Recipe selectedRecipe = (Recipe) recipeSP.getSelectedItem();
				presenter.selectRecipe(selectedRecipe);
			}
		});
	}

	@Override
	public void initializeArgumentsFromIntent() {

	}

	@Override
	public void showErrorMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showSuccessMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setActionBarTitle(String title) {
		setTitle(title);
	}

	private class RecipeSpinnerAdapter extends BaseAdapter {

		private final Context context;
		private final List<Recipe> recipeList;

		RecipeSpinnerAdapter(Context context, List<Recipe> recipeList) {
			this.context = context;
			this.recipeList = recipeList;
		}


		@Override
		public int getCount() {
			return recipeList.size();
		}

		@Override
		public Object getItem(int position) {
			return recipeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return recipeList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RecipeSpinnerViewHolder viewHolder;
			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.adapter_recipe_spinner, parent, false);
				viewHolder = new RecipeSpinnerViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (RecipeSpinnerViewHolder) convertView.getTag();
			}

			viewHolder.recipeNameTV.setText(recipeList.get(position).getNome());

			return convertView;
		}

		class RecipeSpinnerViewHolder {
			TextView recipeNameTV;

			RecipeSpinnerViewHolder(View targetView) {
				recipeNameTV = targetView.findViewById(R.id.tvRecipeName);
			}
		}
	}
}

