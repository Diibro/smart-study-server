package com.smartstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartstudy.model.MyUser;
import com.smartstudy.service.JwtService;
import com.smartstudy.service.UserService;
import com.smartstudy.utils.Mailer;
import com.smartstudy.utils.OTPGenerator;
import com.smartstudy.utils.Responce;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
     @Autowired
     private UserService userService;

     @Autowired
     private JwtService jwtservice;

     @Autowired
     private PasswordEncoder passwordEncoder;

     @PostMapping("/session")
     public ResponseEntity<?> checkSession(@RequestBody String token) {
          System.out.println("Endpoint reached");
          if(token != null) {
               System.out.println(token);
               MyUser user = new MyUser();
               user.setEmail(jwtservice.getUsername(token));
               Responce<MyUser> resUser = userService.search(user);
               if(resUser.getEntity() != null){
                    System.out.println(resUser.getEntity().getEmail());
                    return new ResponseEntity<>(new Responce<MyUser>(resUser.getEntity(), null, "user is logged in", 1), HttpStatus.OK);
               }
          }
          return new ResponseEntity<>(new Responce<MyUser>(null, null, "user is not logged in", 1), HttpStatus.OK);
          
     }

     @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> loginUser(@RequestBody MyUser user, HttpServletResponse response){
          MyUser exisitingUser = userService.search(user).getEntity();
          if(exisitingUser != null){
               if(passwordEncoder.matches(user.getPassword(), exisitingUser.getPassword())){
                    if(exisitingUser.isActive()){
                         String token = jwtservice.generateToken(exisitingUser);
                         Cookie cookie = new Cookie("authToken", token);
                         cookie.setHttpOnly(true);
                         cookie.setSecure(false);
                         cookie.setPath("/");
                         cookie.setMaxAge(2 * 60 * 60);
                         response.addCookie(cookie);
                         return new ResponseEntity<>(new Responce<String>(token,"Login successfully"), HttpStatus.OK);
                    }else{
                         return new ResponseEntity<>(new Responce<String>("First activate your account please"), HttpStatus.OK);
                    }
                    
               }else{
                    return new ResponseEntity<>(new Responce<String>("Invalid password"), HttpStatus.OK);
               }
          }else{
               return new ResponseEntity<>(new Responce<String>("Invalid account information"), HttpStatus.OK);
          }

     }

     @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> loginUser(@RequestBody String token){

          return new ResponseEntity<>("Logout successful", HttpStatus.OK);
     }


     @GetMapping("/")
     public String userPoint(){
          return "You are viewing the users endpoint";
     }

     @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> getAllUsers(){
          List<MyUser> users = userService.findAll();
          return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
     }

     @PostMapping("/activate")
     public ResponseEntity<?> activateUser(@RequestBody MyUser user, HttpServletRequest request){
          Responce<MyUser> res = userService.search(user);
          if(res.getEntity() != null ){
               Integer otp = OTPGenerator.get();
               if(Mailer.send(user.getEmail(), "Verification Code: "+otp, "Verification code for smart study account")){
                    request.getSession().setAttribute("otp", otp);
                    res.setInfo("A verifcation code has been sent to your email");
               }else{
                    res.setInfo("failed to send the verification code. try again later");
               }
               return new ResponseEntity<>(res, HttpStatus.OK);
          }else{
               return new ResponseEntity<>("User does not exist.", HttpStatus.OK);
          }
     }

     @PostMapping(value="/complete-activation",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> completeActivation(@RequestBody Responce<MyUser> activateInfo, HttpServletRequest request) {
          HttpSession session = request.getSession(false);
          if (session != null) {
               MyUser user = (MyUser) session.getAttribute("user");
               Integer otpString = (Integer) session.getAttribute("otp");
     
               if (otpString != null) {
                    try {
                         int otp = otpString;
          
                         if (otp == activateInfo.getCode() && user != null) {
                              user.setActive(true);
                              Responce<MyUser> res = userService.update(user);
                              return new ResponseEntity<>(new Responce<MyUser>(user, "Activation successful", 1), HttpStatus.OK);
                         } else {
                              return new ResponseEntity<>(new Responce<MyUser>(user, "Activation failed", 0), HttpStatus.OK);
                         }
                    } catch (NumberFormatException e) {
                         // Handle the case where the OTP is not a valid integer
                         return new ResponseEntity<>(new Responce<MyUser>(null, "Invalid OTP format", 0), HttpStatus.OK);
                    }
               } else {
                    return new ResponseEntity<>(new Responce<MyUser>(null, "Invalid OTP", 0), HttpStatus.OK);
               }
          } else {
               return new ResponseEntity<>(new Responce<MyUser>(null, "Session is invalid", 0), HttpStatus.OK);
          }
     }


     @PostMapping(value ="/add-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> addUser(@RequestBody MyUser user, HttpServletRequest request){
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          Responce<MyUser> res = userService.save(user);
          if(res.getEntity() != null) {
               int otp = OTPGenerator.get();
               if(Mailer.send(user.getEmail(), "Verification Code: "+otp, "Verification code for smart study account")){
                    request.getSession().setAttribute("otp", otp);
                    res.setCode(otp);
               }
          }
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PutMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> updateUser(@RequestBody MyUser user){
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          Responce<MyUser> res = userService.update(user);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PostMapping(value = "/delete-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> deleteUser(@RequestBody MyUser user){
          Responce<MyUser> res = userService.delete(user);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PostMapping(value = "/search-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> searchUser(@RequestBody MyUser user){
          Responce<MyUser> res = userService.search(user);
          return new ResponseEntity<>(res.getEntity(), HttpStatus.ACCEPTED);
     }
}
