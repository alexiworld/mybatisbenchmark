package com.example.springmybatis3dyna;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class MyBatis3DynaApp {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(MyBatis3DynaApp.class, args);
    }

}
