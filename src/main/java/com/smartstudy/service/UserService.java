package com.smartstudy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartstudy.model.MyUser;
import com.smartstudy.repository.UserRepo;
import com.smartstudy.utils.ModelUtils;
import com.smartstudy.utils.Responce;


@Service
public class UserService implements UserDetailsService {
     @Autowired
     private UserRepo userRepo;

     public Responce<MyUser> save(MyUser user){
          try {
               if(user == null) {
                    return new Responce<MyUser>("Invalid user information");
               }
               Optional<MyUser> savedUser = userRepo.findById(user.getEmail());
               if(savedUser.isPresent()){
                    return new Responce<>("User Already exists");
               }else{
                    MyUser sUser = userRepo.save(user);
                    return new Responce<MyUser>(sUser, "User account created successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<MyUser> update(MyUser user){
          try {
               if(user == null) {
                    return new Responce<MyUser>("Invalid user information");
               }
               Optional<MyUser> ckUser = userRepo.findById(user.getEmail());
               if(!ckUser.isPresent()){
                    return new Responce<>("User not found");
               }else{
                    MyUser sUser = ckUser.get();
                    BeanUtils.copyProperties(user, sUser,ModelUtils.getNullPropertyNames(user));
                    userRepo.save(sUser);
                    return new Responce<MyUser>(sUser, "user updated successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<MyUser> delete(MyUser user){
          try{
               if(user == null) {
                    return new Responce<MyUser>("Invalid user information");
               }
               userRepo.delete(user);
               return new Responce<>("Deleted  the user successfully");
          }catch(Exception e){
               return new Responce<>(e,"Server error");
          }
     }

     public Responce<MyUser> search(MyUser user){
          try {
               if(user == null) {
                    return new Responce<MyUser>("Invalid user information");
               }
               Optional<MyUser> oUser = userRepo.findById(user.getEmail());
               if(oUser.isPresent()){
                    MyUser foundUser = oUser.get();
                    return new Responce<>(foundUser, "user found");
               }
               else{
                    return new Responce<>("Cannot find the user searched");
               }
          } catch (Exception e) {
               e.printStackTrace();
               return new Responce<>(e, "Server error");
          }
     }

     public List<MyUser> findAll(){
          try {
               return userRepo.findAll();
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
     }

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Optional<MyUser> user = userRepo.findById(username);
          if (user.isPresent()) {
               return user.get();
          } else {
               throw new UsernameNotFoundException("User not found with username: " + username);
          }
     }
}
