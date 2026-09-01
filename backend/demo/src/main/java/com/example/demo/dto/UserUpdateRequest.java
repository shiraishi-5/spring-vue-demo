package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.demo.dto.profile.ProfileRequest;

import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "名前は必須です")
    @Size(max = 20, message = "名前は20文字以下で入力して下さい")
    private String name;

    @NotBlank(message = "メールは必須です")
    @Email(message = "メールの形式が正しくありません")
    private String email;

    @Pattern(regexp = "^$|.{8,}$", message = "パスワードは8文字以上で入力してください")
    private String password;
    
    private ProfileRequest profile;
}
