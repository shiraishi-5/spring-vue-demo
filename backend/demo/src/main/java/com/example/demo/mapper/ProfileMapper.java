package com.example.demo.mapper;

import com.example.demo.dto.profile.ProfileRequest;
import com.example.demo.dto.profile.ProfileResponse;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;

import ch.qos.logback.core.util.StringUtil;

public class ProfileMapper {

    //リクエストからエンティティに変換
    public static Profile requestToEntity(User user, ProfileRequest req) {
        Profile profile = new Profile();

        profile.setUser(user);
        
        String reqNickname = req.getNickname();
        String setNickname = null;
        
        if (!StringUtil.isNullOrEmpty(reqNickname)) {
            setNickname = reqNickname;
        }
        
        profile.setNickname(setNickname);
        profile.setBirthday(req.getBirthday());

        return profile;
    }
    
    //空のエンティティを作成
    public static Profile makeEmptyEntity(User user) {
        Profile profile = new Profile();
        
        profile.setUser(user);
        profile.setNickname(null);
        profile.setBirthday(null);
        
        return profile;
    }

    //エンティティからレスポンスに変換
    public static ProfileResponse entityToResponse(Profile profile) {
        return new ProfileResponse(
                profile.getNickname(),
                profile.getBirthday());
    }

    //元のプロファイルにリクエストの情報をセットする
    public static void updateEntity(Profile targetProfile, ProfileRequest requestProfile) {
        targetProfile.setNickname(requestProfile.getNickname());
        targetProfile.setBirthday(requestProfile.getBirthday());
    }
}
