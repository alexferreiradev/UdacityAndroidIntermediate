package com.alex.baking.app.ui.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Alex on 17/03/2017.
 */

public final class ViewUtil {

	public static void showNotImplementedMsg(Context context) {
		Toast.makeText(context, "Função não implementada", Toast.LENGTH_SHORT).show();
	}
}
