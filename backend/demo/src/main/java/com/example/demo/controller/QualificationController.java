package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.QualificationMaster;
import com.example.demo.service.QualificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QualificationController {
    private final QualificationService qualificationService;

    @GetMapping("/qualification-master")
    public ApiResponse<QualificationMaster> getMasters() {
        List<QualificationMaster> masterList = qualificationService.getMasters();
        
        return new ApiResponse<>(
                "マスタ取得完了しました",
                masterList);
    }
}
