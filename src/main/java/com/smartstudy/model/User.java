package com.smartstudy.model;

import java.util.Date;
import java.util.UUID;

import com.smartstudy.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     @Column(name = "user_id")
     private UUID userId;

     @Column(name = "email", nullable = false, unique = true)
     private String email;

     @Column(name = "name")
     private String name;

     @Column(name = "phone")
     private String phone;

     @Column(name = "password")
     private String password;

     @Column(name = "active")
     private boolean active;

     @Enumerated(EnumType.STRING)
     @Column(name = "role")
     private ERole role;

     @Column(name="registration_date")
     private Date registrationDate;
     

}
