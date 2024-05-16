package com.smartstudy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartstudy.model.Task;
import com.smartstudy.repository.TaskRepo;
import com.smartstudy.utils.ModelUtils;
import com.smartstudy.utils.Responce;


@Service
public class TaskService {
     @Autowired
     private TaskRepo taskRepo;

     public Responce<Task> save(Task task){
          try {
               Task savedTask = taskRepo.getReferenceById(task.getTaskId());
               if(savedTask != null){
                    return new Responce<>("Task Already exists");
               }else{
                    savedTask = taskRepo.save(task);
                    return new Responce<Task>(savedTask, "New Task saved successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<Task> update(Task task){
          try {
               Task updateTask = taskRepo.getReferenceById(task.getTaskId());
               if(updateTask == null){
                    return new Responce<>("Task not found");
               }else{
                    BeanUtils.copyProperties(task,updateTask,ModelUtils.getNullPropertyNames(task));
                    taskRepo.save(updateTask);
                    return new Responce<Task>(updateTask, "Task updated successfully");
               }
          } catch (Exception e) {
               return new Responce<>(e,"Server Error");
          }
     }

     public Responce<Task> delete(Task task){
          try{
               taskRepo.delete(task);
               return new Responce<>("Deleted  the task successfully");
          }catch(Exception e){
               return new Responce<>(e,"Server error");
          }
     }

     public Responce<Task> search(Task task){
          try {
               Task foundTask = taskRepo.findById(task.getTaskId()).get();
               if(foundTask == null){
                    return new Responce<>("Cannot find the task searched");
               }else {
                    return new Responce<>(foundTask, "task found");
               }
          } catch (Exception e) {
               e.printStackTrace();
               return new Responce<>(e, "Server error");
          }
     }

     public List<Task> findAll(){
          try {
               return taskRepo.findAll();
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
     }
}
