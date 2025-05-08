package com.lapchenko.recipes.controller;

import com.lapchenko.recipes.exception.InvalidRecipeException;
import com.lapchenko.recipes.exception.NoSuchRecipeException;
import com.lapchenko.recipes.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecipeExceptionHandler {

    @ExceptionHandler(NoSuchRecipeException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchRecipeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Not Found", ex.getMessage()));
    }

    @ExceptionHandler(InvalidRecipeException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(InvalidRecipeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Validation Error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Server Error", "An unexpected error occurred"));
    }
}

