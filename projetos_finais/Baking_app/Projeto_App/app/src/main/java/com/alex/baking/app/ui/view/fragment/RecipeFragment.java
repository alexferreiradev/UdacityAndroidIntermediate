package com.alex.baking.app.ui.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.adapter.IngredientAdapter;
import com.alex.baking.app.ui.adapter.StepAdapter;
import com.alex.baking.app.ui.util.RecipeUtils;
import com.alex.baking.app.ui.view.contract.RecipeContract;

import java.util.List;
import java.util.Objects;

public class RecipeFragment extends BaseFragment<Recipe, RecipeContract.Presenter> implements RecipeContract.FragmentView {

	@BindView(R.id.tvServing)
	TextView servingTV;
	@BindView(R.id.rvIngredient)
	RecyclerView ingredientRV;
	@BindView(R.id.rvStep)
	RecyclerView stepRV;
	@BindView(R.id.ivRecipe)
	ImageView recipeIV;

	private IngredientAdapter ingredientAdapter;
	private StepAdapter stepAdapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recipe, container, false);
		ButterKnife.bind(this, view);
		ingredientRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		stepRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

		return view;
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
	}

	@Override
	public void startView(Recipe model) throws IllegalArgumentException {
		servingTV.setText(model.getServing());
	}

	@Override
	public Recipe destroyView(Recipe model) {
		return null;
	}

	@Override
	public void setPresenter(RecipeContract.Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void bindViewModel(Recipe recipe) {
		servingTV.setText(RecipeUtils.getServing(recipe, Objects.requireNonNull(getContext())));
		if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
			recipeIV.setImageURI(Uri.parse(recipe.getImage()));
		}
	}

	@Override
	public void addIngredientToAdapter(List<Ingredient> ingredientList) {
		if (ingredientAdapter == null) {
			ingredientAdapter = new IngredientAdapter(ingredientList);
			ingredientRV.setAdapter(ingredientAdapter);
			ingredientRV.setVisibility(View.VISIBLE);
		} else {
			ingredientAdapter.addAllModel(ingredientList);
		}
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void addStepToAdapter(List<Step> stepList) {
		if (stepAdapter == null) {
			stepAdapter = new StepAdapter(stepList, new StepAdapter.StepAdapterListener() {
				@Override
				public void selectStepItem(Long stepId, int position) {
					presenter.selectStep(stepId, position);
				}
			}, getContext());
			stepRV.setAdapter(stepAdapter);
		} else {
			stepAdapter.addAllModel(stepList);
		}
	}
}
