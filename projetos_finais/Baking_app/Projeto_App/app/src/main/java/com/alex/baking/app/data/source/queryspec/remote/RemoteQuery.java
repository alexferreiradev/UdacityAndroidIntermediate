package com.alex.baking.app.data.source.queryspec.remote;

import com.alex.baking.app.data.source.queryspec.BaseQuerySpec;

import java.net.URL;

/**
 * Created by Alex on 17/12/2017.
 */

@SuppressWarnings("WeakerAccess")
public abstract class RemoteQuery extends BaseQuerySpec<URL> {

	public RemoteQuery(int mLimit, int mOffset) {
		super(mLimit, mOffset);
	}
}
