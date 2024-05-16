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
import org.springframework.web.bind.annotation.RestController;

import com.smartstudy.model.Resource;
import com.smartstudy.service.ResourceService;
import com.smartstudy.utils.Responce;

@RequestMapping("/api/v1/resource")
@RestController
public class ResourceController {
     @Autowired
     private ResourceService resourceService;

     @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> findAll(){
          return new ResponseEntity<>(resourceService.findAll(), HttpStatus.FOUND);
     }

     @PostMapping(value ="/add-resource", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> addResource(@RequestBody Resource resource){
          Responce<Resource> res = resourceService.save(resource);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @PutMapping(value = "/update-resource", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> updateResource(@RequestBody Resource resource){
          Responce<Resource> res = resourceService.update(resource);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @DeleteMapping(value = "/delete-resource", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> deleteResource(@RequestBody Resource resource){
          Responce<Resource> res = resourceService.delete(resource);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }

     @GetMapping(value = "/search-resource", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> searchResource(@RequestBody Resource resource){
          Responce<Resource> res = resourceService.search(resource);
          return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
     }
}
