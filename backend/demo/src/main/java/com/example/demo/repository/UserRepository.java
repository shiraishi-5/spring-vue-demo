package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNameContaining(String name);

    Page<User> findByNameContainingOrEmailContaining(
            String name,
            String email,
            Pageable pageable);

    @Query("""
            SELECT u
            FROM User u
            LEFT JOIN u.profile p
            WHERE u.name LIKE %:keyword%
               OR u.email LIKE %:keyword%
               OR p.nickname LIKE %:keyword%
            """)
    Page<User> search(
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("""
            SELECT DISTINCT u
            FROM User u
            LEFT JOIN u.profile p
            LEFT JOIN u.userQualifications q
            WHERE (u.name LIKE %:keyword%
               OR u.email LIKE %:keyword%
               OR p.nickname LIKE %:keyword%)
            AND q.qualificationMaster.id IN :qualificationIds
            """)
    Page<User> findHaveAnyQualifications(String keyword, List<Integer> qualificationIds, Pageable pageable);

    @Query(value = """
            SELECT u.*
            FROM users u
            LEFT JOIN profiles p
                ON u.id = p.user_id
            JOIN user_qualifications uq
              ON u.id = uq.user_id
            WHERE (u.name LIKE %:keyword%
               OR u.email LIKE %:keyword%
               OR p.nickname LIKE %:keyword%)
            AND uq.qualification_id IN (:qualificationIds)
            GROUP BY u.id
            HAVING COUNT(DISTINCT uq.qualification_id) = :qualificationCount
            """, nativeQuery = true)
    Page<User> findHavingAllQualifications(
            String keyword,
            List<Integer> qualificationIds,
            int qualificationCount,
            Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
