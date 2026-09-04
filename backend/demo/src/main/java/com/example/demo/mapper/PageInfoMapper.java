package com.example.demo.mapper;

import org.springframework.data.domain.Page;

import com.example.demo.dto.PageInfoResponse;

public class PageInfoMapper {
    
    public static <T> PageInfoResponse toResponse(Page<T> page) {
        return new PageInfoResponse(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
