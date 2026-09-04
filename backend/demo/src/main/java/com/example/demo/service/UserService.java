package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ApiPageResponse;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.dto.profile.ProfileRequest;
import com.example.demo.dto.qualification.QualificationResponse;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.entity.UserQualification;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.PageInfoMapper;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final QualificationService qualificationService;
    private final PasswordEncoder passwordEncoder;

    //登録
    public ApiResponse<UserResponse> save(UserRequest req) {
        //メールアドレス重複確認
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateException("そのメールアドレスは既に登録されています");
        }

        User user = UserMapper.requestToEntity(req);

        //パスワードハッシュ化後、セット
        String hashPassword = passwordEncoder.encode(req.getPassword());
        user.setPassword(hashPassword);

        //プロファイルセット
        Profile profile = profileService.makeProfile(user, req.getProfile());
        user.setProfile(profile);

        //資格セット
        List<UserQualification> qualList = qualificationService.makeUserQualificationList(user,
                req.getQualifications());

        //重複確認
//        qualificationService.isDuplicate(user, qualList);

        user.setUserQualifications(qualList);

        //データベースに保存
        User savedUser = userRepository.save(user);

        //レスポンス作成
        List<QualificationResponse> qualResList = qualificationService.makeQualResList(qualList);
        UserResponse userRes = UserMapper.entityToResponse(savedUser, qualResList);

        return new ApiResponse<>(
                "登録完了しました",
                List.of(userRes));
    }

    //更新
    public ApiResponse<UserResponse> update(int id, UserUpdateRequest req) {
        if (userRepository.existsByEmailAndIdNot(req.getEmail(), id)) {
            throw new DuplicateException("そのメールアドレスは既に登録されています");
        }

        User user = getUser(id);

        UserMapper.updateEntity(user, req);

        if (req.getPassword() != "") {
            //パスワードハッシュ化
            String hashPassword = passwordEncoder.encode(req.getPassword());
            user.setPassword(hashPassword);
        }

        //プロファイル更新
        Profile profile = user.getProfile();
        ProfileRequest reqProfile = req.getProfile();

        ProfileMapper.updateEntity(profile, reqProfile);

        user.setProfile(profile);

        User updatedUser = userRepository.save(user);

        UserResponse userRes = UserMapper.entityToResponse(updatedUser);

        return new ApiResponse<>(
                "更新完了しました",
                List.of(userRes));
    }

    //削除
    public ApiResponse<UserResponse> delete(int id) {
        User user = getUser(id);

        userRepository.delete(user);

        UserResponse userRes = makeUserResponse(user);

        return new ApiResponse<>(
                "削除完了しました",
                List.of(userRes));
    }

    //単ユーザー取得
    public ApiResponse<UserResponse> show(int id) {
        User user = getUser(id);

        UserResponse userRes = makeUserResponse(user);

        return new ApiResponse<>(
                "取得完了しました",
                List.of(userRes));
    }

    //検索　（名前、メール、ニックネーム部分一致、絞り込み）
    public ApiPageResponse<UserResponse> index(String keyword, List<Integer> qualificationIds, String filterType,
            Pageable pageable) {
        Page<User> searchedUsers = null;

        if ("all".equals(filterType) && qualificationIds != null) {
            searchedUsers = userRepository.findHavingAllQualifications(keyword, qualificationIds,
                    qualificationIds.size(), pageable);
        } else if ("any".equals(filterType) && qualificationIds != null) {
            searchedUsers = userRepository.findHaveAnyQualifications(keyword, qualificationIds, pageable);
        } else {
            searchedUsers = userRepository.search(keyword, pageable);
        }

        ArrayList<UserResponse> userResList = new ArrayList<>();

        for (User user : searchedUsers) {
            UserResponse userResponse = makeUserResponse(user);
            userResList.add(userResponse);
        }

        return new ApiPageResponse<>(
                "検索条件に合うユーザーの取得完了しました",
                userResList,
                PageInfoMapper.toResponse(searchedUsers));
    }

    /* ====================
        部品
    ==================== */

    //ユーザーレスポンスを作成　ゲット時に使用
    private UserResponse makeUserResponse(User user) {
        List<UserQualification> qualList = user.getUserQualifications();

        List<QualificationResponse> qualResList = qualificationService
                .makeQualResList(qualList);

        return UserMapper.entityToResponse(user, qualResList);
    }

    //単ユーザーAPI取得
    private User getUser(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("ユーザーが存在しません"));
    }

    //UserからUserResponseへ変換
    private List<UserResponse> changeToResList(List<User> users) {
        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()))
                .toList();
    }

}
