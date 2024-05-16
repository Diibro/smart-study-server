package com.smartstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartstudy.model.Task;
import com.smartstudy.service.TaskService;
import com.smartstudy.utils.Responce;

@RequestMapping("/api/v1/task")
public class TaskController {
     @Autowired
     private TaskService taskService;

     @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> findAll(){
          return new ResponseEntity<>(taskService.findAll(), HttpStatus.FOUND);
     }

     @PostMapping(value ="/add-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> addTask(@RequestBody Task task){
          Responce<Task> res = taskService.save(task);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PutMapping(value = "/update-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> updateTask(@RequestBody Task task){
          Responce<Task> res = taskService.update(task);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @DeleteMapping(value = "/delete-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> deleteTask(@RequestBody Task task){
          Responce<Task> res = taskService.delete(task);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @GetMapping(value = "/search-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> searchTask(@RequestBody Task task){
          Responce<Task> res = taskService.search(task);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }
}
