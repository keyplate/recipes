package com.lapchenko.recipes.repository;

import com.lapchenko.recipes.domain.MealType;
import com.lapchenko.recipes.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE " +
            "(:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:maxTime IS NULL OR r.timeToCook <= :maxTime) AND " +
            "(:mealType IS NULL OR r.mealType = :mealType)")
    List<Recipe> findByNameAndTimeToCookAndMealType(
            @Param("name") String name,
            @Param("maxTime") Long maxTime,
            @Param("mealType") MealType mealType
    );
}
