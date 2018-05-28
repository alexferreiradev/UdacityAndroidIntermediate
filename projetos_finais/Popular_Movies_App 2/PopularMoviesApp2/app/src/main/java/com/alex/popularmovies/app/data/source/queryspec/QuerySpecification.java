package com.alex.popularmovies.app.data.source.queryspec;

/**
 * Created by Alex on 17/12/2017.
 */

public interface QuerySpecification<ReturnType> {

	ReturnType getQuery();

	int getOffset();

	int getLimit();

}
