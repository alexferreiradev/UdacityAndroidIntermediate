package com.alex.baking.app.data.model;

import java.util.List;

public class Recipe extends BaseModel<Recipe> {

	private String nome;
	private String serving;
	private String image;

	private List<Ingredient> ingredientList;
	private List<Step> stepList;

	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}

	public List<Step> getStepList() {
		return stepList;
	}

	public void setStepList(List<Step> stepList) {
		this.stepList = stepList;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getServing() {
		return serving;
	}

	public void setServing(String serving) {
		this.serving = serving;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Recipe)) return false;

		Recipe recipe = (Recipe) o;

		if (nome != null ? !nome.equals(recipe.nome) : recipe.nome != null) return false;
		if (serving != null ? !serving.equals(recipe.serving) : recipe.serving != null) return false;
		if (image != null ? !image.equals(recipe.image) : recipe.image != null) return false;
		if (ingredientList != null ? !ingredientList.equals(recipe.ingredientList) : recipe.ingredientList != null) return false;
		return stepList != null ? stepList.equals(recipe.stepList) : recipe.stepList == null;
	}

	@Override
	public int hashCode() {
		int result = nome != null ? nome.hashCode() : 0;
		result = 31 * result + (serving != null ? serving.hashCode() : 0);
		result = 31 * result + (image != null ? image.hashCode() : 0);
		result = 31 * result + (ingredientList != null ? ingredientList.hashCode() : 0);
		result = 31 * result + (stepList != null ? stepList.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"nome='" + nome + '\'' +
				", serving='" + serving + '\'' +
				", image='" + image + '\'' +
				", ingredientList=" + ingredientList +
				", stepList=" + stepList +
				", id=" + id +
				'}';
	}
}
