package com.lapchenko.recipes.service;

import com.lapchenko.recipes.domain.MealType;
import com.lapchenko.recipes.domain.Recipe;
import com.lapchenko.recipes.exception.InvalidRecipeException;
import com.lapchenko.recipes.exception.NoSuchRecipeException;
import com.lapchenko.recipes.model.RecipeCreateRequest;
import com.lapchenko.recipes.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public List<Recipe> searchRecipe(String name, Long timeToCook, MealType mealType) {
        return repository.findByNameAndTimeToCookAndMealType(name, timeToCook, mealType);
    }

    public Recipe getById(Long id) {
        var optionalRecipe = repository.findById(id);
        return optionalRecipe
                .orElseThrow(() -> new NoSuchRecipeException(String.format("No recipe with id:%d", id)));
    }

    @Transactional
    public Recipe create(RecipeCreateRequest recipeCreateRequest) {
        try {
            var recipe = new Recipe();
            recipe.setName(recipeCreateRequest.getName());
            recipe.setDescription(recipeCreateRequest.getDescription());
            recipe.setIngredients(recipeCreateRequest.getIngredients());
            recipe.setMealType(recipeCreateRequest.getMealType());
            return repository.save(recipe);
        } catch (Exception e) {
            throw new InvalidRecipeException();
        }
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
