package com.alex.baking.app.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

	private List<String> titles;
	private List<Fragment> fragments;

	public DetailViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	//	public DetailViewPagerAdapter(FragmentManager fm, DetailPresenter presenter, Recipe currentRecipe) {
//		super(fm);
//		fragments = new ArrayList<>();
//		titles = new ArrayList<>();
//
////		Bundle fragmentArgument = new Bundle();
////		InfoDetailsFragment infoDetailsFragment = new InfoDetailsFragment(presenter);
////		fragmentArgument.putSerializable(InfoDetailsFragment.Recipe_ARG_KEY, currentRecipe);
////		infoDetailsFragment.setArguments(fragmentArgument);
////		presenter.setInfoView(infoDetailsFragment);
////
////		VideoDetailsFragment videoDetailsFragment = new VideoDetailsFragment(presenter);
////		fragmentArgument.putSerializable(VideoDetailsFragment.Recipe_ARG_KEY, currentRecipe);
////		videoDetailsFragment.setArguments(fragmentArgument);
////		presenter.setVideoView(videoDetailsFragment);
////
////		ReviewDetailsFragment reviewDetailsFragment = new ReviewDetailsFragment(presenter);
////		fragmentArgument.putSerializable(ReviewDetailsFragment.Recipe_ARG_KEY, currentRecipe);
////		reviewDetailsFragment.setArguments(fragmentArgument);
////		presenter.setReviewView(reviewDetailsFragment);
////
////		fragments.add(infoDetailsFragment);
////		fragments.add(videoDetailsFragment);
////		fragments.add(reviewDetailsFragment);
////		titles.add("Informações");
////		titles.add("Vídeos");
////		titles.add("Comentários");
//	}

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
