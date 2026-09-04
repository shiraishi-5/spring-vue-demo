package com.example.demo.dto;

import java.util.List;

public record ApiPageResponse<T>(
        String message,
        List<T> data,
        PageInfoResponse pageInfo
) {
}
