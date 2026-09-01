package com.example.demo.dto;

import java.util.List;

public record ApiResponse<T>(
        String message,
        List<T> data) {
}
