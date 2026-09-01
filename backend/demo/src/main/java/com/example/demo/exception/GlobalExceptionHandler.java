package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ApiErrorsResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //バリデーションエラーのとき
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        ApiErrorsResponse<Map<String, String>> res = new ApiErrorsResponse<>("入力に不備があります", errors);

        return ResponseEntity.badRequest()
                .body(res);
    }

    //存在しないとき
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundError(
            NotFoundException ex) {
        ApiErrorsResponse<Map<String, String>> res = new ApiErrorsResponse<>(ex.getMessage(), Map.of());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(res);
    }

    //重複があるとき
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleUserDuplicateError(
            DuplicateException ex) {
        ApiErrorsResponse<Map<String, String>> res = new ApiErrorsResponse<>(
                "APIエラーがあります",
                Map.of("email", ex.getMessage()));

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(res);
    }
}
