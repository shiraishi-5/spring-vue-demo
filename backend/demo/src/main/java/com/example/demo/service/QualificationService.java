package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.qualification.QualificationRequest;
import com.example.demo.dto.qualification.QualificationResponse;
import com.example.demo.entity.QualificationMaster;
import com.example.demo.entity.User;
import com.example.demo.entity.UserQualification;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.QualificationMapper;
import com.example.demo.repository.QualificationMasterRepository;
import com.example.demo.repository.UserQualificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QualificationService {
    public final QualificationMasterRepository qualificationMasterRepository;
    public final UserQualificationRepository userQualificationRepository;

    //資格マスタをマップで取得
    private Map<Integer, QualificationMaster> getMasters(List<QualificationRequest> req) {
        if (req == null) {
            return Map.of();
        }

        //idリスト作成
        List<Integer> ids = req.stream()
                .map(qual -> qual.getQualificationId())
                .toList();

        //資格マスタのマップを作成
        Map<Integer, QualificationMaster> masters = qualificationMasterRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(
                        QualificationMaster::getId,
                        Function.identity()));

        //存在しないマスタがあるか確認
        if (masters.size() != ids.size()) {
            throw new NotFoundException("存在しない資格IDがあります");
        }

        return masters;
    }

    //資格リストエンティティを作成
    public List<UserQualification> makeUserQualificationList(User user, List<QualificationRequest> req) {
        if (req == null) {
            return List.of();
        }

        //資格マスタを取得する
        Map<Integer, QualificationMaster> masterMap = getMasters(req);

        ArrayList<UserQualification> qualList = new ArrayList<>();

        //資格リクエストを一つ一つ作成し、リストに追加
        for (QualificationRequest qualRequest : req) {
            UserQualification userQual = QualificationMapper.requestToEntity(
                    user,
                    masterMap.get(qualRequest.getQualificationId()),
                    qualRequest.getAcquisitionDate());

            qualList.add(userQual);
        }

        return qualList;
    }

    //資格レスポンスリストの作成
    public List<QualificationResponse> makeQualResList(List<UserQualification> userQualList) {
        return userQualList.stream()
                .map(qual -> QualificationMapper.entityToResponse(qual))
                .toList();
    }

    //すべてのマスタを取得する
    public List<QualificationMaster> getMasters() {
        return qualificationMasterRepository.findAll();
    }

    public void isDuplicate(User user, List<UserQualification> userQualList) {
        if (userQualList == null || userQualList.isEmpty()) {
            return;
        }
        
        //idリスト作成
        List<Integer> ids = userQualList.stream()
                .map(qual -> qual.getId())
                .toList();
        
        List<Integer> duplicateIds =
                userQualificationRepository.findDuplicateQualificationIds(
                        user.getId(),
                        ids);

        if (!duplicateIds.isEmpty()) {
            throw new DuplicateException(
                    "既に登録済みの資格があります"
            );
        }
    }
}
