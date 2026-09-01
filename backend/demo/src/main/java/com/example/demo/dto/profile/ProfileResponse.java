package com.example.demo.dto.profile;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ProfileResponse {
    private String nickname;
    private LocalDate birthday;
    
    public ProfileResponse(String nickname, LocalDate birthday) {
        super();
        this.nickname = nickname;
        this.birthday = birthday;
    }
}
