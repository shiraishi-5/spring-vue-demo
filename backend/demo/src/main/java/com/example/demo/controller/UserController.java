package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiPageResponse;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    //登録
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest userRequest) {
        ApiResponse<UserResponse> res = userService.save(userRequest);

        return ResponseEntity.ok(res);
    }

    //更新
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
            @PathVariable int id) {
        ApiResponse<UserResponse> res = userService.update(id, userUpdateRequest);

        return ResponseEntity.ok(res);
    }

    //削除
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> delete(@PathVariable int id) {
        ApiResponse<UserResponse> res = userService.delete(id);

        return ResponseEntity.ok(res);
    }

    //単ユーザー取得
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> show(@PathVariable int id) {
        ApiResponse<UserResponse> res = userService.show(id);

        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<UserResponse>> index(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Integer> qualificationIds,
            @RequestParam(required = false) String filterType,
            Pageable pageable) {
        ApiPageResponse<UserResponse> reses = userService.index(keyword, qualificationIds, filterType, pageable);

        return ResponseEntity.ok(reses);
    }
}
