package com.lapchenko.recipes.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Ingredient(String name, String amount) {
}
