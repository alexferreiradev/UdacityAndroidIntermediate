package com.alex.baking.app.data.model;

public class Ingredient extends BaseModel {

	private Double quantity;
	private MeasureType measure;
	private String ingredient;

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
		if (o == null || getClass() != o.getClass()) return false;

		Ingredient that = (Ingredient) o;

		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
		if (measure != that.measure) return false;
		return ingredient != null ? ingredient.equals(that.ingredient) : that.ingredient == null;
	}

	@Override
	public int hashCode() {
		int result = quantity != null ? quantity.hashCode() : 0;
		result = 31 * result + (measure != null ? measure.hashCode() : 0);
		result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Ingredient{" +
				"quantity=" + quantity +
				", measure=" + measure +
				", ingredient='" + ingredient + '\'' +
				", id=" + id +
				'}';
	}
}
