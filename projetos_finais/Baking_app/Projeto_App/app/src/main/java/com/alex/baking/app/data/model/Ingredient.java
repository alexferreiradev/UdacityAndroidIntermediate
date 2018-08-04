package com.alex.baking.app.data.model;

@SuppressWarnings("SimplifiableIfStatement")
public class Ingredient extends BaseModel {

	private Double quantity;
	private MeasureType measure;
	private String ingredient;
	private Long recipeId;

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public MeasureType getMeasure() {
		return measure;
	}

	public void setMeasure(MeasureType measure) {
		this.measure = measure;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ingredient)) return false;

		Ingredient that = (Ingredient) o;

		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
		if (measure != that.measure) return false;
		if (ingredient != null ? !ingredient.equals(that.ingredient) : that.ingredient != null) return false;
		return recipeId != null ? recipeId.equals(that.recipeId) : that.recipeId == null;
	}

	@Override
	public int hashCode() {
		int result = quantity != null ? quantity.hashCode() : 0;
		result = 31 * result + (measure != null ? measure.hashCode() : 0);
		result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
		result = 31 * result + (recipeId != null ? recipeId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Ingredient{" +
				"quantity=" + quantity +
				", measure=" + measure +
				", ingredient='" + ingredient + '\'' +
				", recipeId=" + recipeId +
				", id=" + id +
				'}';
	}
}
