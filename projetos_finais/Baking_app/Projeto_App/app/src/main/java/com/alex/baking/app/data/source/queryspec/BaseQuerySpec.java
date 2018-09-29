package com.alex.baking.app.data.source.queryspec;

/**
 * Created by Alex on 17/12/2017.
 */

public abstract class BaseQuerySpec<ReturnType> implements QuerySpecification<ReturnType> {

	protected int mLimit;
	protected int mOffset;

	public BaseQuerySpec(int mLimit, int mOffset) {
		this.mLimit = mLimit;
		this.mOffset = mOffset;
	}

	public int getLimit() {
		return mLimit;
	}

	public int getOffset() {
		return mOffset;
	}
}
