package com.alex.baking.app.ui.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.alex.baking.app.ui.presenter.IPresenter;

public abstract class BaseFragment<ModelType, PresenterType extends IPresenter> extends Fragment implements SimpleFragment<ModelType, PresenterType> {
	private static final String TAG = BaseFragment.class.getSimpleName();
	protected PresenterType presenter;
	protected ProgressBar mProgressBar;

	public BaseFragment() {

	}

	protected abstract void startWithArguments(Bundle arguments);

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(false);
		Log.d(TAG, "Fragment sendo criado: " + this.getClass().getSimpleName());
	}

	@Override
	public void onResume() {
		Log.d(TAG, "Fragment voltando: " + this.getClass().getSimpleName());
		super.onResume();
		Log.d(TAG, "Fragment esta ativo");
	}

	@Override
	public void onStart() {
		Log.d(TAG, "Fragment iniciando: " + this.getClass().getSimpleName());
		super.onStart();
		Log.d(TAG, "Fragment iniciando com argumentos: " + this.getClass().getSimpleName());
		startWithArguments(getArguments());
		Log.d(TAG, "Fragment iniciado");
	}

	@Override
	public void showProgressBar(int progress) {
		if (mProgressBar != null) {
			mProgressBar.setVisibility(View.VISIBLE);
			if (progress < 0) {
				mProgressBar.setIndeterminate(true);
			} else {
				mProgressBar.setProgress(progress);
			}
		}
	}

	@Override
	public void onDestroyView() {
		destroyView(null);
		super.onDestroyView();
	}

	@Override
	public void hideProgressBar() {
		mProgressBar.setVisibility(View.GONE);
	}
}
