package com.lapchenko.recipes.model;

import com.lapchenko.recipes.domain.Ingredient;
import com.lapchenko.recipes.domain.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RecipeCreateRequest {
    @NotBlank(message = "Recipe name is required")
    @Size(max = 100, message = "Recipe name must be less than 100 characters")
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    @NotNull(message = "Time to cook is required")
    @Min(value = 1, message = "Time to cook should be positive")
    private Long timeToCook;
    private MealType mealType;

    public RecipeCreateRequest() {
    }

    public RecipeCreateRequest(String name, String description, List<Ingredient> ingredients, Long timeToCook, MealType mealType) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.timeToCook = timeToCook;
        this.mealType = mealType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getTimeToCook() {
        return timeToCook;
    }

    public void setTimeToCook(Long timeToCook) {
        this.timeToCook = timeToCook;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
