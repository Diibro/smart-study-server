package com.smartstudy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Responce<Entity> {
     private Entity entity;
     private Exception error;
     private String info;
     private Integer code;

     public Responce(){
     }

     public Responce(Entity tEntity, Exception e, String info){
          this.entity = tEntity;
          this.error = e;
          this.info = info;
     }

     public Responce(String info){
          this.entity = null;
          this.error = null;
          this.info = info;
          this.code = null;
     }

     public Responce( Exception e, String info){
          this.entity = null;
          this.error = e;
          this.info = info;
          this.code = null;
     }

     public Responce( Entity t, String info){
          this.entity = t;
          this.error = null;
          this.info = info;
          this.code = null;
     }

     public Responce( Entity t, String info, Integer code){
          this.entity = t;
          this.error = null;
          this.info = info;
          this.code = code;
     }

}
