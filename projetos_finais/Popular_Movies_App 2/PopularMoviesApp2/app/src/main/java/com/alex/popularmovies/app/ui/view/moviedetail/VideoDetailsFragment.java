package com.alex.popularmovies.app.ui.view.moviedetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.Video;
import com.alex.popularmovies.app.ui.adapter.VideoListAdapter;
import com.alex.popularmovies.app.ui.presenter.IPresenter;
import com.alex.popularmovies.app.ui.presenter.detail.DetailContract;
import com.alex.popularmovies.app.ui.view.fragment.BaseListFragment;

import java.util.List;

@SuppressLint("ValidFragment")
public class VideoDetailsFragment extends BaseListFragment<Video, VideoDetailsFragment.VideoFragmentCallBacks> implements DetailContract.VideoView {
	public static final String MOVIE_ARG_KEY = "movie_key";
	private static final String TAG = VideoDetailsFragment.class.getSimpleName();
	private TextView mEmptyTV;
	private ListView mVideosLV;
	private VideoListAdapter mAdapter;
	private Movie mMovie;

	public VideoDetailsFragment(VideoFragmentCallBacks presenter) {
		super(presenter);
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
		mMovie = (Movie) arguments.get(MOVIE_ARG_KEY);
		if (mMovie == null) {
			throw new IllegalArgumentException("Filme passado para fragment esta nulo");
		}

		presenter.loadVideosFromMovie(mMovie);
	}

	@Override
	public void onStart() {
		super.onStart();
		presenter.loadVideosFromMovie(mMovie);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.video_fragment_movie_details, container, false);
		mProgressBar = fragmentView.findViewById(R.id.progressBar);
		mEmptyTV = fragmentView.findViewById(R.id.tvEmpty);
		mVideosLV = fragmentView.findViewById(R.id.lvVideos);

		mEmptyTV.setText("Não há videos");
		mVideosLV.setEmptyView(mEmptyTV);

		return fragmentView;
	}

	@Override
	protected void createAdapter(List<Video> model) {
		mAdapter = new VideoListAdapter(getActivity(), model, presenter);
		mVideosLV.setAdapter(mAdapter);
	}

	@Override
	protected List<Video> destroyAdapter() {
		mAdapter = null;
		mVideosLV.setAdapter(null);

		return null;
	}

	@Override
	public void openVideo(Video video) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http")
				.authority("youtube.com")
				.appendPath("watch")
				.appendQueryParameter("v", video.getKey());
		intent.setData(builder.build());

		startActivity(intent);
	}


	public interface VideoFragmentCallBacks extends IPresenter {

		void playVideo(Video video);

		void loadVideosFromMovie(Movie movie);

	}
}
