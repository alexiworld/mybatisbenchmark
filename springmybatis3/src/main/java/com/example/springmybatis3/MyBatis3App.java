package com.example.springmybatis3;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class MyBatis3App {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(MyBatis3App.class, args);
    }

}
