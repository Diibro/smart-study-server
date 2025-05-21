package com.smartstudy.controller;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartstudy.model.Course;
import com.smartstudy.service.CourseService;
import com.smartstudy.utils.Responce;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
     @Autowired
     private CourseService courseService;

     @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> findAll(){
          return new ResponseEntity<>(courseService.findAll(), HttpStatus.FOUND);
     }

     @PostMapping(value ="/add-course", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> addCourse(@RequestBody Course course){
          Responce<Course> res = courseService.save(course);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PutMapping(value = "/update-course", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> updateCourse(@RequestBody Course course){
          Responce<Course> res = courseService.update(course);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PostMapping(value = "/delete-course", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> deleteCourse(@RequestBody Course course){
          Responce<Course> res = courseService.delete(course);
          System.out.println(res.getInfo());
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PostMapping(value = "/search-course", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> searchCourse(@RequestBody Course course){
          Responce<Course> res = courseService.search(course);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }
}
