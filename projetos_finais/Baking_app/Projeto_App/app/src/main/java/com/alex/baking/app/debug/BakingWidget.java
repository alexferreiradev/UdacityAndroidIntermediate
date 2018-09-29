package com.alex.baking.app.debug;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import com.alex.baking.app.R;
import com.alex.baking.app.debug.config.BakingWidgetConfigureActivity;
import com.alex.baking.app.debug.config.WidgetConfigurationUtil;
import com.alex.baking.app.ui.view.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BakingWidgetConfigureActivity BakingWidgetConfigureActivity}
 */
public class BakingWidget extends AppWidgetProvider {
	private static final String TAG = BakingWidget.class.getSimpleName();

	public static void sendUpdateBroadcastToAllWidgets(Context context) {
		int allWidgetIds[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, BakingWidget.class));
		Intent intent = new Intent(context, BakingWidget.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		context.sendBroadcast(intent);
	}

	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

		Long recipeId = WidgetConfigurationUtil.loadRecipeId(context, appWidgetId);
		String recipeName = WidgetConfigurationUtil.loadRecipeName(context, appWidgetId);
		if (recipeId != null) {
			views.setTextViewText(R.id.tvRecipeTitle, String.format("%s %s:", context.getString(R.string.title_prefix_recipe_widget), recipeName));
			views.setEmptyView(R.id.lvIngredient, R.id.tvEmpty);

			Intent intent = new Intent(context, BakingWidgetService.class);
			intent.putExtra(RecipeActivity.RECIPE_ID_EXTRA_PARAM_KEY, recipeId);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			views.setRemoteAdapter(appWidgetId, R.id.lvIngredient, intent);

			Intent intentToRecipeAct = new Intent(context, RecipeActivity.class);
			intentToRecipeAct.putExtra(RecipeActivity.RECIPE_ID_EXTRA_PARAM_KEY, recipeId);
			intentToRecipeAct.setAction(String.valueOf(appWidgetId));
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToRecipeAct, PendingIntent.FLAG_UPDATE_CURRENT);
			views.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvIngredient);
		} else {
			Log.w(TAG, "ID de recipe nao foi encontrado para este widget: " + appWidgetId);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			Log.v(TAG, "Atualizando widget: " + appWidgetId);
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			WidgetConfigurationUtil.deleteConfig(context, appWidgetId);
		}
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}
}

