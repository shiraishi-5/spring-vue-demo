package com.example.demo.dto.qualification;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class QualificationResponse {
    private Integer qualificationId;
    private String qualificationName;
    private LocalDate acquisitionDate;
    
    public QualificationResponse(Integer qualificationId, LocalDate acquisitionDate) {
        super();
        this.qualificationId = qualificationId;
        this.acquisitionDate = acquisitionDate;
    }

    public QualificationResponse(Integer qualificationId, String qualificationName, LocalDate acquisitionDate) {
        this(qualificationId, acquisitionDate);
        this.qualificationName = qualificationName;
    }
}
