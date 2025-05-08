package com.lapchenko.recipes.exception;

public class NoSuchRecipeException extends RuntimeException {
    public NoSuchRecipeException() {
    }

    public NoSuchRecipeException(String message) {
        super(message);
    }
}
