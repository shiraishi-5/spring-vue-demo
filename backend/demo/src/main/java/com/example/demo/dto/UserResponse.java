package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.profile.ProfileResponse;
import com.example.demo.dto.qualification.QualificationResponse;

import lombok.Getter;

@Getter
public class UserResponse {
    private int id;
    private String name;
    private String email;
    private ProfileResponse profile;
    private List<QualificationResponse> qualifications;

    public UserResponse(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserResponse(int id, String name, String email, ProfileResponse profile) {
        this(id, name, email);
        this.profile = profile;
    }
    
    public UserResponse(int id, String name, String email, ProfileResponse profile, List<QualificationResponse> qualifications) {
        this(id,name,email,profile);
        this.qualifications = qualifications;
    }
}
