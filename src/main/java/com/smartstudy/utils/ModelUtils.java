package com.smartstudy.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class ModelUtils {
     public static String[] getNullPropertyNames(Object source) {
          final BeanWrapper src = new BeanWrapperImpl(source);
          java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
     
          Set<String> emptyNames = new HashSet<>();
          for (java.beans.PropertyDescriptor pd : pds) {
               Object srcValue = src.getPropertyValue(pd.getName());
               if (srcValue == null) emptyNames.add(pd.getName());
          }
          String[] result = new String[emptyNames.size()];
          return emptyNames.toArray(result);
     }
}
