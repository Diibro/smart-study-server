// package com.smartstudy.config;

// import java.util.Arrays;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import com.smartstudy.enums.ERole;
// import com.smartstudy.service.MyUserDetailsService;

// @Configuration
// @EnableWebSecurity
// public class AppSecurityConfig {
//      @Autowired
//      private MyUserDetailsService myUserDetailsService;

//      @Bean
//      public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
//           return httpSecurity
//                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(registry->{
//                registry.requestMatchers(
//                     "/api/v1/user/add-user", 
//                     "/api/v1/user/login",
//                     "/api/v1/user/logout",
//                     "/api/v1/user/search-user",
//                     "/api/v1/user/complete-activation",
//                     "/api/v1/user/session"
//                     ).permitAll();
//                registry.requestMatchers("/admin/**").hasRole(ERole.ADMIN.toString());
//                registry.requestMatchers(
//                     "/api/v1/course/**",
//                     "/api/v1/resource/**",
//                     "/api/v1/task/**",
//                     "/api/v1/user/**"
//                     ).hasAnyRole(ERole.ADMIN.toString(),ERole.USER.toString());
//                registry.anyRequest().authenticated();
//           })
//           .formLogin(formLogin -> formLogin.permitAll())
//           .sessionManagement(sessionManagement -> sessionManagement
//                     .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                )
//           .build();
//      }

//      @Bean
//      public UserDetailsService userDetailsService(){
//           return myUserDetailsService;
//      } 

//      @Bean
//      public AuthenticationProvider authenticationProvider() {
//           DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//           provider.setUserDetailsService(myUserDetailsService);
//           provider.setPasswordEncoder(passwordEncoder());
//           return provider;
//      }

//      @Bean 
//      public PasswordEncoder passwordEncoder () {
//           return new BCryptPasswordEncoder();
//      }

//      @Bean
//      public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//           CorsConfiguration configuration = new CorsConfiguration();
//           configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173","http://localhost:6000")); // Adjust this to your frontend URL
//           configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//           configuration.setAllowedHeaders(Arrays.asList("*"));
//           configuration.setAllowCredentials(true);

//           UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//           source.registerCorsConfiguration("/**", configuration);
//           return source;
//      }
// }
