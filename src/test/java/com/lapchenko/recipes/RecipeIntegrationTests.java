package com.lapchenko.recipes;

import com.lapchenko.recipes.domain.Ingredient;
import com.lapchenko.recipes.domain.MealType;
import com.lapchenko.recipes.domain.Recipe;
import com.lapchenko.recipes.model.RecipeCreateRequest;
import com.lapchenko.recipes.repository.RecipeRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIntegrationTests {

    @Value("${server.servlet.context-path}")
    private String apiPrefix;
    @LocalServerPort
    private int port;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private RecipeRepository repository;

    @BeforeEach
    public void truncate() {
        repository.deleteAll();
    }

    @Test
    public void recipesCreated() {
        var recipe = new RecipeCreateRequest(
                "Cookie",
                "A delicious chocolate cookie",
                List.of(new Ingredient("Sugar", "200g"),
                        new Ingredient("Flour", "500g"),
                        new Ingredient("Eggs", "5pc"),
                        new Ingredient("Butter", "2005")),
                1000L,
                MealType.LUNCH
        );

        var homeURL = "http://localhost:" + port + apiPrefix;
        var url = homeURL + "/recipes";
        var response = restTemplate.postForObject(URI.create(url), recipe, Recipe.class);

        Assertions.assertEquals("Cookie", response.getName());
    }

    @Test
    public void recipeDeleted() {
        var recipe = new RecipeCreateRequest(
                "Cookie",
                "A delicious chocolate cookie",
                List.of(new Ingredient("Sugar", "200g"),
                        new Ingredient("Flour", "500g"),
                        new Ingredient("Eggs", "5pc"),
                        new Ingredient("Butter", "2005")),
                1000L,
                MealType.LUNCH
        );

        var homeURL = "http://localhost:" + port + apiPrefix;
        var urlCreate = homeURL + "/recipes";
        var createResponse = restTemplate.postForObject(URI.create(urlCreate), recipe, Recipe.class);

        var urlDelete = homeURL + "/recipes/";
        restTemplate.delete(URI.create(urlDelete + createResponse.getId()));

        Assertions.assertEquals(0, repository.findAll().size());
    }

    @Test
    public void recipeFoundByNameAndMealType() {
        var recipe1 = new RecipeCreateRequest(
                "Cookie",
                "",
                Collections.emptyList(),
                1000L,
                MealType.LUNCH
        );
        var recipe2= new RecipeCreateRequest(
                "Oats",
                "",
                Collections.emptyList(),
                1000L,
                MealType.BREAKFAST
        );

        var homeURL = "http://localhost:" + port + apiPrefix;
        var createUrl = homeURL + "/recipes";
        restTemplate.postForObject(URI.create(createUrl), recipe1, Recipe.class);
        restTemplate.postForObject(URI.create(createUrl), recipe2, Recipe.class);

        var searchNameUrl = homeURL + "/recipes/search?name=";
        var cookieSearchTerm = "Cookie";

        var searchNameResponse = restTemplate.getForObject(URI.create(searchNameUrl + cookieSearchTerm), Recipe[].class);
        Assertions.assertEquals("Cookie", searchNameResponse[0].getName());

        var searchMealTypeUrl = homeURL + "/recipes/search?mealType=";
        var breakfastSearchTerm = "BREAKFAST";

        var searchMealTypeResponse = restTemplate.getForObject(URI.create(searchMealTypeUrl + breakfastSearchTerm), Recipe[].class);
        Assertions.assertEquals("Oats", searchMealTypeResponse[0].getName());


    }
}
