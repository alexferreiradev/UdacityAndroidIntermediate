package com.alex.baking.app.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> implements RecycleViewAdaper<Step> {

	private List<Step> stepList;

	public StepAdapter(List<Step> stepList) {
		this.stepList = stepList;
	}

	@NonNull
	@Override
	public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.adapter_step, parent, false);

		return new StepViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
		Step step = stepList.get(position);
		holder.shorDescriptionTV.setText(step.getShortDescription());
	}

	@Override
	public int getItemCount() {
		return stepList.size();
	}

	@Override
	public void addAllModel(List<Step> models) {
		stepList.addAll(models);
		notifyDataSetChanged();
	}

	@Override
	public void removeAllModel(List<Step> models) {
		stepList.removeAll(models);
		notifyDataSetChanged();
	}

	static class StepViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.tvShortDescription)
		TextView shorDescriptionTV;

		StepViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
