package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import lombok.Data;

@Data
@Entity
@Table(name = "profiles")
@SQLDelete(sql = "UPDATE profiles SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private LocalDate birthday;
    
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
}
