package com.smartstudy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartstudy.model.Resource;
import com.smartstudy.repository.ResourceRepo;
import com.smartstudy.utils.ModelUtils;
import com.smartstudy.utils.Responce;

@Service
public class ResourceService {
     @Autowired
     private ResourceRepo resourceRepo;

     public Responce<Resource> save(Resource resource){
          try {
               Resource res = resourceRepo.save(resource);
               if(res != null){
                    return new Responce<Resource>(res, null, "Resource saved successfully");
               }else{
                    return new Responce<Resource>("Failed to save the resource. Try again");
               }
          } catch (Exception e) {
               return new Responce<Resource>(e,"Server error");
          }
          
     }

     public Responce<Resource> update(Resource resource){
          try {

               Resource resourseToUpdate = resourceRepo.getReferenceById(resource.getResourceId());
               if(resourseToUpdate != null){
                    BeanUtils.copyProperties(resource, resourseToUpdate, ModelUtils.getNullPropertyNames(resource));
                    resourceRepo.save(resourseToUpdate);
                    return new Responce<Resource>(resourseToUpdate, null, "Resource update successfully");
               }else{
                    return new Responce<Resource>("Failed to update the resource. Try again");
               }
          } catch (Exception e) {
               return new Responce<Resource>(e,"Server error");
          }
          
     }

     public Responce<Resource> delete(Resource resource){
          try {
               resourceRepo.delete(resource);
               return new Responce<Resource>("Reource deleted successfully");
          } catch (Exception e) {
               return new Responce<Resource>(e,"Server error");
          }
          
     }

     public Responce<Resource> search(Resource resource){
          try {
               Resource res = resourceRepo.findById(resource.getResourceId()).get();
               if(res != null){
                    return new Responce<Resource>(res, null, "Resource found.");
               }else{
                    return new Responce<Resource>("Cannot find the resource. Try again!");
               }
          } catch (Exception e) {
               return new Responce<Resource>(e,"Server error");
          }
          
     }

     public List<Resource> findAll(){
          try {
               return resourceRepo.findAll();
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
          
     }
}
