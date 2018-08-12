package com.alex.baking.app.ui.adapter;

import android.content.Context;
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
	private StepAdapterListener listener;
	private Context context;
	private int lastSelected = -1;

	public StepAdapter(List<Step> stepList, StepAdapterListener listener, Context context) {
		this.stepList = stepList;
		this.listener = listener;
		this.context = context;
	}

	@NonNull
	@Override
	public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.adapter_step, parent, false);

		return new StepViewHolder(view);
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
		Step step = stepList.get(position);
		if (position == lastSelected) {
			setItemToSelected(holder);
		}
		holder.shorDescriptionTV.setText(step.getShortDescription());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lastSelected = holder.getAdapterPosition();
				setItemToSelected(holder);
				listener.selectStepItem(step.getId(), holder.getAdapterPosition());
			}
		});
	}

	private void setItemToSelected(@NonNull StepViewHolder holder) {
		holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
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

	public interface StepAdapterListener {
		void selectStepItem(Long stepId, int position);
	}
}
