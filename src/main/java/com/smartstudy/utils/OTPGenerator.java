package com.smartstudy.utils;

import java.util.Random;

public class OTPGenerator {
     public static int get() {
          Random random = new Random();
          int num = 100000 + random.nextInt(900000);
          return num;
     }
}
