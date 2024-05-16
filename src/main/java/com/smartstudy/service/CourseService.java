package com.smartstudy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartstudy.model.Course;
import com.smartstudy.repository.CourseRepo;
import com.smartstudy.utils.ModelUtils;
import com.smartstudy.utils.Responce;

@Service
public class CourseService {
     @Autowired
     private CourseRepo courseRepo;

     public Responce<Course> save(Course course){
          try {
               Course savedCourse = courseRepo.getReferenceById(course.getCourseId());
               if(savedCourse != null){
                    return new Responce<>("Course Already exists");
               }else{
                    savedCourse = courseRepo.save(course);
                    return new Responce<Course>(savedCourse, "Course saved successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<Course> update(Course course){
          try {
               Course updateCourse = courseRepo.getReferenceById(course.getCourseId());
               if(updateCourse == null){
                    return new Responce<>("Course does not exist");
               }else{
                    BeanUtils.copyProperties(course,updateCourse,ModelUtils.getNullPropertyNames(course));
                    courseRepo.save(updateCourse);
                    return new Responce<Course>(updateCourse, "Course saved successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<Course> delete(Course course){
          try{
               courseRepo.delete(course);
               return new Responce<>("Delete  the course successfully");
          }catch(Exception e){
               return new Responce<>(e,"Server error");
          }
     }

     public Responce<Course> search(Course course){
          try {
               Course foundCourse = courseRepo.findById(course.getCourseId()).get();
               if(foundCourse == null){
                    return new Responce<>("Cannot the course searched");
               }else {
                    return new Responce<>(foundCourse, "Course found");
               }
          } catch (Exception e) {
               e.printStackTrace();
               return new Responce<>(e, "Server error");
          }
     }

     public List<Course> findAll(){
          try {
               return courseRepo.findAll();
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
     }
}
