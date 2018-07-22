package com.alex.baking.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Video;
import com.alex.baking.app.ui.view.moviedetail.VideoDetailsFragment;

import java.util.List;

/**
 * Created by Alex on 25/03/2017.
 */

public class VideoListAdapter extends BaseAdapter implements ListViewAdaper<Video> {
	private List<Video> videos;
	private Context context;
	private VideoDetailsFragment.VideoFragmentCallBacks callBacks;

	public VideoListAdapter(Context context, List<Video> videos, VideoDetailsFragment.VideoFragmentCallBacks callBacks) {
		this.videos = videos;
		this.context = context;
		this.callBacks = callBacks;
	}

	@Override
	public int getCount() {
		return videos.size();
	}

	@Override
	public Object getItem(int position) {
		return videos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			assert inflater != null;
			convertView = inflater.inflate(R.layout.adapter_video_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.playIV = convertView.findViewById(R.id.ivPlayVideo);
			viewHolder.videoNameTV = convertView.findViewById(R.id.tvVideoName);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Video video = videos.get(position);
		viewHolder.playIV.setOnClickListener(v -> callBacks.playVideo(video));
		viewHolder.videoNameTV.setText(video.getName());

		return convertView;
	}

	@Override
	public void addAllModel(List<Video> models) {
		videos.addAll(models);
		notifyDataSetChanged();
	}

	@Override
	public void removeAllModel(List<Video> models) {
		videos.removeAll(models);
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		private ImageView playIV;
		private TextView videoNameTV;
	}
}
