package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.profile.ProfileRequest;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    //プロファイルエンティティ作成
    public Profile makeProfile(User user, ProfileRequest req) {
        Profile profile = null;

        if (req != null) {
            profile = ProfileMapper.requestToEntity(user, req);
        } else {
            profile = ProfileMapper.makeEmptyEntity(user);
        }

        return profile;
    }

    /* ====================
    部品
    ==================== */
}
