package com.balajischool.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Balaji High School — Digital Ecosystem Application Runner
 */
@SpringBootApplication(scanBasePackages = "com.balajischool")
public class BalajiSchoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(BalajiSchoolApplication.class, args);
        System.out.println("==========================================================");
        System.out.println("  BALAJI HIGH SCHOOL — Digital Ecosystem Online (2026)   ");
        System.out.println("  Santhamaguluru Block, Prakasam District, Andhra Pradesh ");
        System.out.println("==========================================================");
    }
}
