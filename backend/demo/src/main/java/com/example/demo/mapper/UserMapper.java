package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.dto.qualification.QualificationResponse;
import com.example.demo.entity.User;

public class UserMapper {
    //リクエストからエンティティに変換
    public static User requestToEntity(UserRequest req) {
        User user = new User();

        user.setName(req.getName());
        user.setEmail(req.getEmail());

        return user;
    }

    //エンティティからレスポンス
    public static UserResponse entityToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                ProfileMapper.entityToResponse(user.getProfile()));
    }

    //エンティティからレスポンス　資格情報追加
    public static UserResponse entityToResponse(User user, List<QualificationResponse> qualResList) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                ProfileMapper.entityToResponse(user.getProfile()),
                qualResList);
    }

    //元のユーザーにリクエストの情報をセットする
    public static void updateEntity(User targetUser, UserUpdateRequest reqestUser) {
        targetUser.setName(reqestUser.getName());
        targetUser.setEmail(reqestUser.getEmail());
    }
}
