package com.smartstudy.model;

import java.util.Date;
import java.util.UUID;

import com.smartstudy.enums.EStatus;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Task {
     @Id
     @Column(name = "task_id")
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID taskId;

     @Column(name = "task_title", nullable = false)
     private String taskTitle;

     @Column(name = "task_description")
     private String taskDescription;

     @Column(name = "start_date")
     private Date startDate;

     @Column(name = "end_date")
     private Date endDate;

     @Column(name="status")
     private EStatus status;

     @Column(name = "active")
     private boolean active;
}
