package com.example.demo.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.demo.dto.profile.ProfileRequest;
import com.example.demo.dto.qualification.QualificationRequest;

import lombok.Data;

@Data
public class UserRequest {
    private final int PASSWORD_MIN_LENGTH = 8;
    
    @NotBlank(message = "名前は必須です")
    @Size(max = 20, message = "名前は20文字以下で入力して下さい")
    private String name;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスの形式が正しくありません")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = PASSWORD_MIN_LENGTH, message = "パスワードは" + PASSWORD_MIN_LENGTH + "文字以上で入力して下さい")
    private String password;
    
    private ProfileRequest profile;
    
    private List<QualificationRequest> qualifications;
}
