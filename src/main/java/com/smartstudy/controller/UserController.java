package com.smartstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartstudy.model.User;
import com.smartstudy.service.UserService;
import com.smartstudy.utils.Responce;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
     @Autowired
     private UserService userService;

     @GetMapping("/")
     public String userPoint(){
          return "You are viewing the users endpoint";
     }

     @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> getAllUsers(){
          List<User> users = userService.findAll();
          return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
     }

     @PostMapping(value ="/add-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> addUser(@RequestBody User user){
          Responce<User> res = userService.save(user);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PutMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> updateUser(@RequestBody User user){
          Responce<User> res = userService.update(user);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @DeleteMapping(value = "/delete-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> deleteUser(@RequestBody User user){
          Responce<User> res = userService.delete(user);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @GetMapping(value = "/search-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> searchUser(@RequestBody User user){
          Responce<User> res = userService.search(user);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }
}
