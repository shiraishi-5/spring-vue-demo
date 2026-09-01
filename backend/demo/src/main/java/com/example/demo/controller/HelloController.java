package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.UserForm;

@Controller
public class HelloController {

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@Valid @ModelAttribute("userForm") UserForm formData,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        model.addAttribute("userForm", formData);
        return "result";
    }
}
