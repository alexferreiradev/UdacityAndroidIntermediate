package com.alex.popularmovies.app.data.source.queryspec.sql;

import com.alex.popularmovies.app.data.repository.movie.MovieRepository.MovieFilter;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.sql.PMContract;

public class MoviesLocalQuery implements QuerySpecification<SqlQuery> {

	private int limit;
	private int offset;
	private MovieFilter filter;

	public MoviesLocalQuery(int limit, int offset, MovieFilter filter) {
		this.limit = limit;
		this.offset = offset;
		this.filter = filter;
	}

	@Override
	public SqlQuery getQuery() {
		SqlQuery.SqlQueryBuilder queryBuilder = SqlQuery.builder();
		queryBuilder.setUri(PMContract.MovieEntry.CONTENT_URI);

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
