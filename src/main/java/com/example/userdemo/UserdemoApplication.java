package com.example.userdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.userdemo.model.repository")
public class UserdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserdemoApplication.class, args);
	}

}
