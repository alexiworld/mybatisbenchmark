package com.example.springmybatis2.controller;

import com.example.springmybatis2.domain.Todo;
import com.example.springmybatis2.domain.TodoMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/benchmark")
public class BenchmarkController {

    @Autowired
    TodoMapper todoMapper;

    private final static int ITERATIONS = 100000;

    @Data
    static class Benchmark {
        String version = "MyBatis2#" + ITERATIONS;
        long findAll;
        long findBy;
        long inserts;
        long updates;
        long deletes;
    }

    @GetMapping
    public Benchmark benchmark() {
        todoMapper.init();

        StopWatch insertsTime = new StopWatch("inserts");
        insertsTime.start();
        for (int i = 1; i <= ITERATIONS; i++) {
            Todo todo = new Todo();
            todo.setTodoTitle("ToDo#" + i);
            todo.setFinished(true);
            todo.setCreatedAt(LocalDateTime.now());
            todoMapper.insert(todo);
        }
        insertsTime.stop();
        System.out.println("[MyBatis2][" + ITERATIONS + "]Insert Time: " + insertsTime.getTotalTimeMillis());

        StopWatch findAllTime = new StopWatch("findAll");
        findAllTime.start();
        for (int i = 1; i <= ITERATIONS; i++) {
            todoMapper.findAll();
        }
        findAllTime.stop();
        System.out.println("[MyBatis2][" + ITERATIONS + "]FindAll Time: " + findAllTime.getTotalTimeMillis());

        List<Todo> todos = new ArrayList<>(ITERATIONS);
        StopWatch findByTime = new StopWatch("findBy");
        findByTime.start();
        for (int i = 1; i <= ITERATIONS; i++) {
            todos.add(todoMapper.findById(i).get());
        }
        findByTime.stop();
        System.out.println("[MyBatis2][" + ITERATIONS + "]FindBy Time: " + findByTime.getTotalTimeMillis());

        StopWatch updatesTime = new StopWatch("updates");
        updatesTime.start();
        todos.stream().forEach( todo -> {
                    todo.setTodoTitle(todo.getTodoTitle()+"M");
                    todoMapper.update(todo);
                }
        );
        updatesTime.stop();
        System.out.println("[MyBatis2][" + ITERATIONS + "]Updates Time: " + updatesTime.getTotalTimeMillis());

        StopWatch deletesTime = new StopWatch("deletes");
        deletesTime.start();
        todos.stream().forEach( todo -> {
                    todoMapper.deleteById(todo.getTodoId());
                }
        );
        deletesTime.stop();
        System.out.println("[MyBatis2][" + ITERATIONS + "]Delete Time: " + deletesTime.getTotalTimeMillis());

        Benchmark benchmark = new Benchmark();
        benchmark.findBy = findByTime.getTotalTimeMillis();
        benchmark.findAll = findAllTime.getTotalTimeMillis();
        benchmark.inserts = insertsTime.getTotalTimeMillis();
        benchmark.updates = updatesTime.getTotalTimeMillis();
        benchmark.deletes = deletesTime.getTotalTimeMillis();

        return benchmark;
    }

}