package com.alex.baking.app.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.adapter.IngredientAdapter;
import com.alex.baking.app.ui.adapter.StepAdapter;
import com.alex.baking.app.ui.view.contract.RecipeContract;

import java.util.List;

public class RecipeFragment extends BaseFragment<Recipe, RecipeContract.Presenter> implements RecipeContract.FragmentView {

	@BindView(R.id.tvServing)
	TextView servingTV;
	@BindView(R.id.rvIngredient)
	RecyclerView ingredientRV;
	@BindView(R.id.rvStep)
	RecyclerView stepRV;

	private IngredientAdapter ingredientAdapter;
	private StepAdapter stepAdapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recipe, container, false);
		ButterKnife.bind(this, view);

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
		servingTV.setText(recipe.getServing());
	}

	@Override
	public void addIngredientToAdapter(List<Ingredient> ingredientList) {
		if (ingredientAdapter == null) {
			ingredientAdapter = new IngredientAdapter(ingredientList);
			ingredientRV.setAdapter(ingredientAdapter);
		} else {
			ingredientAdapter.addAllModel(ingredientList);
		}
	}

	@Override
	public void addStepToAdapter(List<Step> stepList) {
		if (stepAdapter == null) {
			stepAdapter = new StepAdapter(stepList);
			stepRV.setAdapter(stepAdapter);
		} else {
			stepAdapter.addAllModel(stepList);
		}
	}
}
