package com.alex.baking.app.debug;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.source.sql.BakingContract;
import com.alex.baking.app.data.source.sql.IngredientSqlSource;
import com.alex.baking.app.ui.view.RecipeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BakingWidgetService extends RemoteViewsService {
	private static final String TAG = BakingRemoteViewFactory.class.getSimpleName();

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new BakingRemoteViewFactory(getApplicationContext(), intent);
	}

	class BakingRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

		List<Ingredient> ingredientList = new ArrayList<>();
		private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
		private Context context;
		private Long recipeId = -1L;
		private Cursor cursor;

		BakingRemoteViewFactory(Context context, Intent intent) {
			this.context = context;
			Bundle extras = intent.getExtras();
			if (extras != null) {
				recipeId = extras.getLong(RecipeActivity.RECIPE_ID_EXTRA_PARAM_KEY, -1L);
				appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
				Log.d(TAG, "Iniciando factory com recipeId: " + recipeId);
			}
		}

		private void loadIngredientFromCursor() {
			if (cursor != null && cursor.moveToFirst()) {
				Log.d(TAG, "Carregando um total de ingredients: " + cursor.getCount());
				while (cursor.moveToNext()) {
					Ingredient ingredient = IngredientSqlSource.cursorToIngredient(cursor);

					ingredientList.add(ingredient);
				}
			} else {
				Log.e(TAG, "Erro ao carregar ingredientes para adapter do widget");
			}
		}

		@Override
		public void onCreate() {
			Uri contentUri = BakingContract.IngredientEntry.buildIngredientByRecipeIdUri(recipeId);
			cursor = getContentResolver().query(contentUri, null, null, null, null);
			AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvIngredient);
			// We sleep for 3 seconds here to show how the empty view appears in the interim.
			// The empty view is set in the StackWidgetProvider and should be a sibling of the
			// collection view.
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onDataSetChanged() {
			ingredientList.clear();
			loadIngredientFromCursor();
		}

		@Override
		public void onDestroy() {
			cursor.close();
			ingredientList.clear();
		}

		@Override
		public int getCount() {
			return ingredientList.size();
		}

		@Override
		public RemoteViews getViewAt(int position) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.adapter_ingredient);
			Ingredient ingredient = ingredientList.get(position);
			Log.d(TAG, "Criando View para ingredient: " + position);
			remoteViews.setTextViewText(R.id.tvIngredient, ingredient.getIngredient());
			String quantity = String.format(Locale.getDefault(), "%.2f %s", ingredient.getQuantity(), ingredient.getMeasure().name().toLowerCase());
			remoteViews.setTextViewText(R.id.tvQuantity, quantity);

			return remoteViews;
		}

		@Override
		public RemoteViews getLoadingView() {
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public long getItemId(int position) {
			return ingredientList.get(position).getId();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
}
