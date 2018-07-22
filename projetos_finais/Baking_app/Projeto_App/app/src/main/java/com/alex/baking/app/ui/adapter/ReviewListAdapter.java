package com.alex.baking.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Review;
import com.alex.baking.app.ui.view.moviedetail.ReviewDetailsFragment;

import java.util.List;

/**
 * Created by Alex on 25/03/2017.
 */

public class ReviewListAdapter extends BaseAdapter implements ListViewAdaper<Review> {
	private List<Review> reviewList;
	private Context context;
	private ReviewDetailsFragment.ReviewFragmentCallbacks callBacks;

	public ReviewListAdapter(Context context, List<Review> reviewList, ReviewDetailsFragment.ReviewFragmentCallbacks callBacks) {
		this.reviewList = reviewList;
		this.context = context;
		this.callBacks = callBacks;
	}

	@Override
	public int getCount() {
		return reviewList.size();
	}

	@Override
	public Object getItem(int position) {
		return reviewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return -1L;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			assert inflater != null;
			convertView = inflater.inflate(R.layout.adapter_review_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.authorTV = convertView.findViewById(R.id.tvAuthorName);
			viewHolder.contentTV = convertView.findViewById(R.id.tvContent);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Review review = reviewList.get(position);
		viewHolder.authorTV.setOnClickListener(v -> callBacks.openReview(review));
		viewHolder.authorTV.setText(review.getAuthor());
		viewHolder.contentTV.setText(review.getContent());

		return convertView;
	}

	@Override
	public void addAllModel(List<Review> models) {
		reviewList.addAll(models);
		notifyDataSetChanged();
	}

	@Override
	public void removeAllModel(List<Review> models) {
		reviewList.removeAll(models);
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		private TextView authorTV;
		private TextView contentTV;
	}
}
