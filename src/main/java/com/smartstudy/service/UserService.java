package com.smartstudy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartstudy.model.User;
import com.smartstudy.repository.UserRepo;
import com.smartstudy.utils.ModelUtils;
import com.smartstudy.utils.Responce;


@Service
public class UserService {
     @Autowired
     private UserRepo userRepo;

     public Responce<User> save(User user){
          try {
               User savedUser = userRepo.getReferenceById(user.getEmail());
               if(savedUser != null){
                    return new Responce<>("User Already exists");
               }else{
                    savedUser = userRepo.save(user);
                    return new Responce<User>(savedUser, "User account created successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<User> update(User user){
          try {
               User User = userRepo.getReferenceById(user.getEmail());
               if(User == null){
                    return new Responce<>("User not found");
               }else{
                    BeanUtils.copyProperties(user, User, ModelUtils.getNullPropertyNames(user));
                    userRepo.save(User);
                    return new Responce<User>(User, "user updated successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<User> delete(User user){
          try{
               userRepo.delete(user);
               return new Responce<>("Deleted  the user successfully");
          }catch(Exception e){
               return new Responce<>(e,"Server error");
          }
     }

     public Responce<User> search(User user){
          try {
               User foundUser = userRepo.findById(user.getEmail()).get();
               if(foundUser == null){
                    return new Responce<>("Cannot find the user searched");
               }else {
                    return new Responce<>(foundUser, "user found");
               }
          } catch (Exception e) {
               e.printStackTrace();
               return new Responce<>(e, "Server error");
          }
     }

     public List<User> findAll(){
          try {
               return userRepo.findAll();
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
     }
}
