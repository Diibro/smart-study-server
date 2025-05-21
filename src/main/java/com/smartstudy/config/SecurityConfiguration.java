package com.smartstudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.smartstudy.enums.ERole;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.http.HttpMethod.GET;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

     private static final String[] WHITE_LIST_URL = {
          "/api/v1/user/add-user", 
          "/api/v1/user/login",
          "/api/v1/user/logout",
          "/api/v1/user/search-user",
          "/api/v1/user/complete-activation",
          "/api/v1/user/session",
          "/api/v1/course/add-course",
          "/v3/api-docs",
          "/v3/api-docs/**",
          "/swagger-ui/**",
          "/swagger-ui/index.html",
     };

     @Autowired
     private final JwtAuthenticationFilter jwtAuthFilter;
     @Autowired
     private final AuthenticationProvider authenticationProvider;
     @Autowired
     private final CorsConfigurationSource corsConfigurationSource;

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource))
                    .authorizeHttpRequests(req -> req
                         .requestMatchers(WHITE_LIST_URL).permitAll()
                         .requestMatchers(GET, "/api/user/").hasAnyAuthority(ERole.ADMIN.toString(), ERole.USER.toString())
                         .anyRequest().authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

          return http.build();
     }

     @Bean
     public AccessDeniedHandler accessDeniedHandler() {
          return (request, response, accessDeniedException) -> {
               response.setStatus(HttpStatus.FORBIDDEN.value());
               response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
          };
     }
}
