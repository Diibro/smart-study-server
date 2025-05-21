package com.smartstudy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartstudy.model.MyUser;
import com.smartstudy.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
     @Autowired
     private UserRepo userRepo;

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          
          Optional<MyUser> user = userRepo.findById(username);
          if(user.isPresent()){
               MyUser ckUser = user.get();
               return User.builder()
               .username(ckUser.getEmail())
               .password(ckUser.getPassword())
               .roles(ckUser.getRole().toString())
               .build();
          }else{
               throw new UsernameNotFoundException(username);
          }
     }
     
}
