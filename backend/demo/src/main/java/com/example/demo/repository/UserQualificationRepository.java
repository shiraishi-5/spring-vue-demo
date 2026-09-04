package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.UserQualification;

public interface UserQualificationRepository extends JpaRepository<UserQualification, Integer> {
    boolean existsByUserIdAndQualificationMasterId(
            Integer userId,
            Integer qualificationId);

    @Query("""
                SELECT uq.qualificationMaster.id
                FROM UserQualification uq
                WHERE uq.user.id = :userId
                  AND uq.qualificationMaster.id IN (:ids)
            """)
    List<Integer> findDuplicateQualificationIds(
            @Param("userid") Integer userId,
            @Param("ids") List<Integer> ids);
}
