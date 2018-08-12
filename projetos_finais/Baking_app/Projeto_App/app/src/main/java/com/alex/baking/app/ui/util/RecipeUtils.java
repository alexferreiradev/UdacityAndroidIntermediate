package com.alex.baking.app.ui.util;

import android.content.Context;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;

public final class RecipeUtils {

	public static String getServing(Recipe recipe, Context context) {
		String serving = recipe.getServing();
		String serveSuffix = context.getString(R.string.serving_suffix);
		String servePreffix = context.getString(R.string.serving_preffix);
		if (Integer.valueOf(serving) == 1) {
			serveSuffix = serveSuffix.replaceAll("pessoas", "pessoa");
		}

		return String.format("%s %s %s", servePreffix, recipe.getServing(), serveSuffix);
	}
}
