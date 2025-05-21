package com.smartstudy.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartstudy.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements UserDetails {
     
     @Id
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
     
     @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
     private List<Course> courses;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
     }

     @Override
     public String getUsername() {
          return this.email;
     }

     @Override
     public boolean isAccountNonExpired() {
          return this.active;
     }

     @Override
     public boolean isAccountNonLocked() {
          return this.active;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return this.active;
     }

     @Override
     public boolean isEnabled() {
          return this.active;
     }


}
