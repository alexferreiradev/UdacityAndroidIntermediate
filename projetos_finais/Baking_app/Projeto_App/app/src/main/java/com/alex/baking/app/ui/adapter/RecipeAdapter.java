package com.alex.baking.app.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.ui.view.contract.MainContract;

import java.util.List;

@SuppressWarnings("Convert2Lambda")
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> implements RecycleViewAdaper<Recipe> {

	private List<Recipe> recipeList;
	private MainContract.Presenter presenter;
	private Context context;

	public RecipeAdapter(List<Recipe> recipeList, MainContract.Presenter presenter, Context context) {
		this.recipeList = recipeList;
		this.presenter = presenter;
		this.context = context;
	}

	@NonNull
	@Override
	public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.adapter_recipe, parent, false);

		return new RecipeViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
		Recipe recipe = recipeList.get(position);
		holder.nameTV.setText(recipe.getNome());
		holder.servingTV.setText(recipe.getServing());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.selectItemClicked(recipe, holder.getAdapterPosition());
			}
		});

		String recipeImage = recipe.getImage();
		if (recipeImage != null && !recipeImage.isEmpty()) {
			holder.recipeIV.setImageURI(Uri.parse(recipeImage));
		}
	}

	@Override
	public int getItemCount() {
		return recipeList.size();
	}

	@Override
	public void addAllModel(List<Recipe> models) {
		recipeList.addAll(models);
		notifyDataSetChanged();
	}

	@Override
	public void removeAllModel(List<Recipe> models) {
		recipeList.removeAll(models);
		notifyDataSetChanged();
	}

	static class RecipeViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.ivRecipe)
		ImageView recipeIV;
		@BindView(R.id.tvName)
		TextView nameTV;
		@BindView(R.id.tvServing)
		TextView servingTV;

		RecipeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
