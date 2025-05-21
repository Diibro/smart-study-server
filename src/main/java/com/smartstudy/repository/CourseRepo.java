package com.smartstudy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartstudy.model.Course;

public interface CourseRepo extends JpaRepository<Course,UUID> {
     
}
