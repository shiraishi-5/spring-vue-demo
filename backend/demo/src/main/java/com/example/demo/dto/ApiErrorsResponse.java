package com.example.demo.dto;

public record ApiErrorsResponse<T>(
        String message,
        T errors) {
}