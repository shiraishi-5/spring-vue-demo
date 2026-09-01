package com.example.demo.mapper;

import java.time.LocalDate;

import com.example.demo.dto.qualification.QualificationResponse;
import com.example.demo.entity.QualificationMaster;
import com.example.demo.entity.User;
import com.example.demo.entity.UserQualification;

public class QualificationMapper {
    public static UserQualification requestToEntity(User user, QualificationMaster master, LocalDate date) {
        UserQualification qual = new UserQualification();

        qual.setUser(user);
        qual.setQualificationMaster(master);
        qual.setAcquisitionDate(date);

        return qual;
    }
    
    public static QualificationResponse entityToResponse(UserQualification qual) {
        return new QualificationResponse(
                qual.getQualificationMaster().getId(),
                qual.getQualificationMaster().getQualificationName(),
                qual.getAcquisitionDate());
    }

    public static QualificationResponse entityToResponse(UserQualification qual, String qualName) {
        return new QualificationResponse(
                qual.getQualificationMaster().getId(),
                qualName,
                qual.getAcquisitionDate());
    }
}
