package com.smartstudy.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Check;

import com.smartstudy.enums.EStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="course")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Check(constraints = "start_date < end_date")
public class Course {
     @Id
     @Column(name="course_id")
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID courseId;

     @Column(name = "course_name", nullable = false)
     private String courseName;

     @Column(name="course_code", nullable = false, unique = true)
     private String courseCode;

     @Column(name = "start_date")
     private Date startDate;

     @Column(name = "end_date")
     private Date endDate;

     @Enumerated(EnumType.STRING)
     @Column(name = "status")
     private EStatus status;

     @Column(name="active")
     private boolean active;

     @ManyToOne
     @JoinColumn(name="user_email")
     private MyUser user; 

     @OneToMany(mappedBy="course", fetch = FetchType.EAGER)
     private List<Resource> resources;

     @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
     private List<Task> task;
}
