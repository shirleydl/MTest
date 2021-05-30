package com.shirleydl.mtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shirleydl.mtest.mapper")
public class MtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtestApplication.class, args);
	}

}
