package com.smartstudy.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartstudy.repository.UserRepo;


@Configuration
@Service
public class ApplicationConfig {

     @Autowired
     private UserRepo userRepository;

     @Bean
     public UserDetailsService userDetailsService() {
          return username -> userRepository.findById(username)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
     }

     @Bean
     public AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
          authenticationProvider.setUserDetailsService(userDetailsService());
          authenticationProvider.setPasswordEncoder(passwordEncoder());
          return authenticationProvider;
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
          return config.getAuthenticationManager();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }
}
