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
import com.alex.baking.app.data.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> implements RecycleViewAdaper<Ingredient> {

	private List<Ingredient> ingredientList;

	public IngredientAdapter(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}

	@NonNull
	@Override
	public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.adapter_ingredient, parent, false);

		return new IngredientViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
		Ingredient ingredient = ingredientList.get(position);
		holder.ingredientTV.setText(ingredient.getIngredient());
		String ingredientQuatity = String.format("%s %s", ingredient.getQuantity(), ingredient.getMeasure().name().toLowerCase());
		holder.quatityTV.setText(ingredientQuatity);
	}

	@Override
	public int getItemCount() {
		return ingredientList.size();
	}

	@Override
	public void addAllModel(List<Ingredient> models) {
		ingredientList.addAll(models);
		notifyDataSetChanged();
	}

	@Override
	public void removeAllModel(List<Ingredient> models) {
		ingredientList.removeAll(models);
		notifyDataSetChanged();
	}

	static class IngredientViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.tvIngredient)
		TextView ingredientTV;
		@BindView(R.id.tvQuantity)
		TextView quatityTV;

		IngredientViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
