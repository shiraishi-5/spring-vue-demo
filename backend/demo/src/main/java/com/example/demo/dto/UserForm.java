package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
    @NotBlank(message = "名前は必須入力です")
    @Size(max = 20, message = "名前は20文字以下で入力して下さい")
    private String name;

    @Min(value = 0, message = "年齢を正しく入力して下さい")
    @Max(value = 120, message = "年齢は0～120歳で入力して下さい")
    private Integer age;
}
