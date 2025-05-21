package com.smartstudy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartstudy.model.Task;

public interface TaskRepo extends JpaRepository<Task, UUID> {
     
}
