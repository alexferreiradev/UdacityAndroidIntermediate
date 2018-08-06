package com.alex.baking.app.debug.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.alex.baking.app.data.model.Recipe;

public final class WidgetConfigurationUtil {
	private static final String TAG = WidgetConfigurationUtil.class.getSimpleName();

	private static final String PREFIX_WIDGET_CONFIG_RECIPE_ID = "widget_config_recipe_id_";
	private static final String PREFIX_WIDGET_CONFIG_RECIPE_NAME = "widget_config_recipe_name_";

	public static void saveRecipeConfig(Recipe recipe, int widgetId, Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.edit()
				.putLong(PREFIX_WIDGET_CONFIG_RECIPE_ID + widgetId, recipe.getId()) // Um para cada widget criado
				.putString(PREFIX_WIDGET_CONFIG_RECIPE_NAME + widgetId, recipe.getNome()) // Um para cada widget criado
				.apply();

		Log.i(TAG, "Recipe configurado para widget: " + widgetId + "-" + recipe.getNome());
	}

	public static Long loadRecipeId(Context context, int appWidgetId) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		long recipeId = prefs.getLong(PREFIX_WIDGET_CONFIG_RECIPE_ID + appWidgetId, -1);
		if (recipeId < 0) {
			Log.w(TAG, "Erro ao ler recipe ID. Lido id invalido");
			return null;
		} else {
			return recipeId;
		}
	}

	public static String loadRecipeName(Context context, int appWidgetId) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String recipeName = prefs.getString(PREFIX_WIDGET_CONFIG_RECIPE_NAME + appWidgetId, "nãoLido");
		if (recipeName.equals("nãoLido")) {
			Log.w(TAG, "Erro ao ler nome de prato");
			return "";
		} else {
			return recipeName;
		}
	}

	public static void deleteConfig(Context context, int appWidgetId) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.edit()
				.remove(PREFIX_WIDGET_CONFIG_RECIPE_ID + appWidgetId) // Um para cada widget criado
				.remove(PREFIX_WIDGET_CONFIG_RECIPE_NAME + appWidgetId) // Um para cada widget criado
				.apply();

		Log.i(TAG, "Removido config para widget: " + appWidgetId);
	}
}
