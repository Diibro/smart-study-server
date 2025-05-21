package com.smartstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartstudy.model.MyUser;

public interface UserRepo extends JpaRepository<MyUser, String>{
     
}
