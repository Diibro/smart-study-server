package com.smartstudy.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
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
public class User implements UserDetails {
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
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
     }

     @Override
     public String getUsername() {
          return this.email;
     }

     @Override
     public boolean isAccountNonExpired() {
          return this.isActive();
     }

     @Override
     public boolean isAccountNonLocked() {
          return this.isActive();
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return this.isActive();
     }

     @Override
     public boolean isEnabled() {
          return this.isActive();
     }

}
