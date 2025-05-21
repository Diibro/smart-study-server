package com.smartstudy.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resourse")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resource {
     @Id
     @Column(name="resource_id")
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID resourceId;

     @Column(name = "resource_name")
     private String resourceName;

     @Column(name = "resource_url")
     private String resourceURL;

     @Column(name="registration-date")
     private Date registrationDate;

     @Column(name="active")
     private boolean active;

     @ManyToOne
     @JoinColumn(name = "course_id")
     private Course course;
}
