package com.smartstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartstudy.model.User;

public interface UserRepo extends JpaRepository<User, String>{
     
}
