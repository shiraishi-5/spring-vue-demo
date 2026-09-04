package com.example.demo.dto;

public record PageInfoResponse(
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPages
) {
}