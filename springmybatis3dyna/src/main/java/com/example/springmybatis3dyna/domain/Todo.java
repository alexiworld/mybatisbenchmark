package com.example.springmybatis3dyna.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Todo {
    @Id
    private Integer todoId;
    private String todoTitle;
    private boolean finished;
    private LocalDateTime createdAt;
}