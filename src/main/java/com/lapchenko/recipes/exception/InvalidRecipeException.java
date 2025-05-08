package com.lapchenko.recipes.exception;

public class InvalidRecipeException extends RuntimeException {
    public InvalidRecipeException() {
    }

    public InvalidRecipeException(String message) {
        super(message);
    }
}
