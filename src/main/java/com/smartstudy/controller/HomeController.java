package com.smartstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {
     @GetMapping(name = "/")
     public String home(){
          return "home.jsp";
     }
}
