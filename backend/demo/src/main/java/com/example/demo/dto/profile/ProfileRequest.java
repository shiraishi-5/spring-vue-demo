package com.example.demo.dto.profile;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProfileRequest {
    private String nickname;
    private LocalDate birthday;
}
