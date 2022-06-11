package com.cyj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.cyj.mapper")
@SpringBootApplication
public class InFuzhouApplication {

    public static void main(String[] args) {
        SpringApplication.run(InFuzhouApplication.class, args);
    }

}
