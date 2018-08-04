package com.alex.baking.app.data.source.queryspec.sql;

import com.alex.baking.app.data.source.queryspec.BaseQuerySpec;

public class BaseSqlSpecification extends BaseQuerySpec<SqlQuery> {

	private SqlQuery sqlQuery;

	public BaseSqlSpecification(SqlQuery sqlQuery, int mLimit, int mOffset) {
		super(mLimit, mOffset);
		this.sqlQuery = sqlQuery;
	}

	@Override
	public SqlQuery getQuery() {
		return sqlQuery;
	}
}
