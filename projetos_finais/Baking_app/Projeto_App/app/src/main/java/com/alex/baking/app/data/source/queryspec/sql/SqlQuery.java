package com.alex.baking.app.data.source.queryspec.sql;

import android.net.Uri;

import java.util.Arrays;

public class SqlQuery {

	private Uri uri;
	private String[] projection;
	private String selection;
	private String[] selectionArgs;
	private String sort;

	private SqlQuery() {
	}

	public static SqlQueryBuilder builder() {
		return new SqlQueryBuilder(new SqlQuery());
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	public String[] getProjection() {
		return projection;
	}

	public void setProjection(String[] projection) {
		this.projection = projection;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String[] getSelectionArgs() {
		return selectionArgs;
	}

	public void setSelectionArgs(String[] selectionArgs) {
		this.selectionArgs = selectionArgs;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SqlQuery sqlQuery = (SqlQuery) o;

		if (uri != null ? !uri.equals(sqlQuery.uri) : sqlQuery.uri != null) return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if (!Arrays.equals(projection, sqlQuery.projection)) return false;
		if (selection != null ? !selection.equals(sqlQuery.selection) : sqlQuery.selection != null) return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if (!Arrays.equals(selectionArgs, sqlQuery.selectionArgs)) return false;
		return sort != null ? sort.equals(sqlQuery.sort) : sqlQuery.sort == null;
	}

	@Override
	public int hashCode() {
		int result = uri != null ? uri.hashCode() : 0;
		result = 31 * result + Arrays.hashCode(projection);
		result = 31 * result + (selection != null ? selection.hashCode() : 0);
		result = 31 * result + Arrays.hashCode(selectionArgs);
		result = 31 * result + (sort != null ? sort.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "SqlQuery{" +
				"uri=" + uri +
				", projection=" + Arrays.toString(projection) +
				", selection='" + selection + '\'' +
				", selectionArgs=" + Arrays.toString(selectionArgs) +
				", sort='" + sort + '\'' +
				'}';
	}

	public static class SqlQueryBuilder {

		private SqlQuery sqlQuery;

		SqlQueryBuilder(SqlQuery sqlQuery) {
			this.sqlQuery = sqlQuery;
		}

		public SqlQueryBuilder setUri(Uri uri) {
			sqlQuery.setUri(uri);
			return this;
		}

		public SqlQueryBuilder setProjection(String[] projection) {
			sqlQuery.setProjection(projection);
			return this;
		}

		public SqlQueryBuilder setSelection(String selection) {
			sqlQuery.setSelection(selection);
			return this;
		}

		public SqlQueryBuilder setSelectionArgs(String[] selectionArgs) {
			sqlQuery.setSelectionArgs(selectionArgs);
			return this;
		}

		public SqlQueryBuilder setSort(String sort) {
			sqlQuery.setSort(sort);
			return this;
		}

		public SqlQuery build() {
			return sqlQuery;
		}
	}
}
