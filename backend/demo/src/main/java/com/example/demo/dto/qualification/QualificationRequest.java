package com.example.demo.dto.qualification;

import java.time.LocalDate;

import lombok.Data;

@Data
public class QualificationRequest {
    private Integer qualificationId;
    private LocalDate acquisitionDate;
}
