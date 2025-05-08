package com.lapchenko.recipes.controller;

import com.lapchenko.recipes.domain.MealType;
import com.lapchenko.recipes.domain.Recipe;
import com.lapchenko.recipes.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class RecipeController {

    private RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> create(@RequestBody Recipe recipe) {
        var createdRecipe = service.create(recipe);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRecipe.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(createdRecipe);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Recipe> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipes/search")
    public ResponseEntity<List<Recipe>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long timeToCook,
            @RequestParam(required = false) MealType mealType) {
        return ResponseEntity.ok(service.searchRecipe(name, timeToCook, mealType));
    }
}
