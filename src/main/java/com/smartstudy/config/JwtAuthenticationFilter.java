package com.smartstudy.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartstudy.service.JwtService;
import com.smartstudy.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

     @Autowired
     private JwtService jwtService;

     @Autowired
     private UserService userService;

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

     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
               throws ServletException, IOException {

          String requestPath = request.getRequestURI();

          // Skip filter for whitelisted URLs
          if (isWhitelisted(requestPath)) {
               filterChain.doFilter(request, response);
               return;
          }

          try {
               String jwt = getTokenFromCookies(request);
               System.out.println("I want the token buddy: "+ jwt);

               if (jwt == null) {
                    sendUnauthorizedResponse(response, "I want jwt token please....");
                    return;
               }

               String userEmail = jwtService.getUsername(jwt);
               if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(userEmail);

                    if (jwtService.isTokenValid(jwt, userDetails)) {
                         UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                              null, userDetails.getAuthorities());
                         authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                         SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                         sendUnauthorizedResponse(response, "Invalid JWT token.");
                         return;
                    }
               }
               filterChain.doFilter(request, response);
          } catch (Exception e) {
               e.printStackTrace();
               sendUnauthorizedResponse(response, "Unauthorized:........" + e.getMessage());
          }
     }

     private boolean isWhitelisted(String requestPath) {
          return Arrays.stream(WHITE_LIST_URL)
                    .anyMatch(url -> url.endsWith("/**")
                         ? requestPath.startsWith(url.substring(0, url.length() - 3))
                         : requestPath.equals(url));
     }

     private String getTokenFromCookies(HttpServletRequest request) {
          // Cookie[] cookies = request.getCookies();
          // if (cookies != null) {
          //      for (Cookie cookie : cookies) {
          //           if ("token".equals(cookie.getName())) {
          //                return cookie.getValue();
          //           }
          //      }
          // }
          // return null;

          // Retrieve the token from the "Authorization" header
          String authHeader = request.getHeader("Authorization");

          // Check if the Authorization header is present and starts with "Bearer "
          if (authHeader != null && authHeader.startsWith("Bearer ")) {
               // Extract the token by removing the "Bearer " prefix
               return authHeader.substring(7);
          }

          // Return null if the token is not present
          return null;
     }

     private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
          SecurityContextHolder.clearContext();
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\": \"" + message + "\"}");
     }

}