package com.smartstudy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartstudy.model.Resource;

public interface ResourceRepo extends JpaRepository<Resource, UUID> {
     
}
