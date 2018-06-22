package com.alex.popularmovies.app.ui.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.ui.presenter.detail.DetailPresenter;
import com.alex.popularmovies.app.ui.view.moviedetail.InfoDetailsFragment;
import com.alex.popularmovies.app.ui.view.moviedetail.ReviewDetailsFragment;
import com.alex.popularmovies.app.ui.view.moviedetail.VideoDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

	private List<String> titles;
	private List<Fragment> fragments;

	public DetailViewPagerAdapter(FragmentManager fm, DetailPresenter presenter, Movie currentMovie) {
		super(fm);
		fragments = new ArrayList<>();
		titles = new ArrayList<>();

		Bundle fragmentArgument = new Bundle();
		InfoDetailsFragment infoDetailsFragment = new InfoDetailsFragment(presenter);
		fragmentArgument.putSerializable(InfoDetailsFragment.MOVIE_ARG_KEY, currentMovie);
		infoDetailsFragment.setArguments(fragmentArgument);
		presenter.setInfoView(infoDetailsFragment);

		VideoDetailsFragment videoDetailsFragment = new VideoDetailsFragment(presenter);
		fragmentArgument.putSerializable(VideoDetailsFragment.MOVIE_ARG_KEY, currentMovie);
		videoDetailsFragment.setArguments(fragmentArgument);
		presenter.setVideoView(videoDetailsFragment);

		ReviewDetailsFragment reviewDetailsFragment = new ReviewDetailsFragment(presenter);
		fragmentArgument.putSerializable(ReviewDetailsFragment.MOVIE_ARG_KEY, currentMovie);
		reviewDetailsFragment.setArguments(fragmentArgument);
		presenter.setReviewView(reviewDetailsFragment);

		fragments.add(infoDetailsFragment);
		fragments.add(videoDetailsFragment);
		fragments.add(reviewDetailsFragment);
		titles.add("Informações");
		titles.add("Vídeos");
		titles.add("Comentários");
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}
}
