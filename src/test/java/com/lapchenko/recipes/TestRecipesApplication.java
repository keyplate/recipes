package com.lapchenko.recipes;

import org.springframework.boot.SpringApplication;

public class TestRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.from(RecipesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
