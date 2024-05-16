package com.smartstudy.utils;

import lombok.Data;

@Data
public class Responce<Entity> {
     private Entity entity;
     private Exception error;
     private String info;

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
     }

     public Responce( Exception e, String info){
          this.entity = null;
          this.error = e;
          this.info = info;
     }

     public Responce( Entity t, String info){
          this.entity = t;
          this.error = null;
          this.info = info;
     }

}
