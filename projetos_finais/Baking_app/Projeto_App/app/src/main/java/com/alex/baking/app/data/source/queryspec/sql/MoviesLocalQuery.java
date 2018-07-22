package com.alex.baking.app.data.source.queryspec.sql;

import android.app.SearchManager;
import android.net.Uri;
import com.alex.baking.app.data.repository.movie.MovieRepository.MovieFilter;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.sql.PMContract;

import java.util.List;

public class MoviesLocalQuery implements QuerySpecification<SqlQuery> {

	private int limit;
	private int offset;
	private String selection;
	private List<String> selectionArgs;
	private MovieFilter filter;

	public MoviesLocalQuery(int limit, int offset, MovieFilter filter) {
		this.limit = limit;
		this.offset = offset;
		this.filter = filter;
	}

	public MoviesLocalQuery(int limit, int offset, String selection, List<String> selectionArgs, MovieFilter filter) {
		this.limit = limit;
		this.offset = offset;
		this.selection = selection;
		this.selectionArgs = selectionArgs;
		this.filter = filter;
	}

	@Override
	public SqlQuery getQuery() {
		SqlQuery.SqlQueryBuilder queryBuilder = SqlQuery.builder();
		String limitParam = offset + "," + limit;
		Uri uri = PMContract.MovieEntry.CONTENT_URI.buildUpon().appendQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT, limitParam).build();
		queryBuilder.setUri(uri);
		if (selection != null && !selection.isEmpty()) {
			queryBuilder.setSelection(selection);
			queryBuilder.setSelectionArgs(selectionArgs.toArray(new String[0]));
		}

		return queryBuilder.build();
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MoviesLocalQuery that = (MoviesLocalQuery) o;

		if (limit != that.limit) return false;
		if (offset != that.offset) return false;
		return filter == that.filter;
	}

	@Override
	public int hashCode() {
		int result = limit;
		result = 31 * result + offset;
		result = 31 * result + (filter != null ? filter.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "MoviesLocalQuery{" +
				"limit=" + limit +
				", offset=" + offset +
				", filter=" + filter +
				'}';
	}
}
