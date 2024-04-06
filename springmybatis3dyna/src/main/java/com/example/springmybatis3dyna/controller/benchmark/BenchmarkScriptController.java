package com.example.springmybatis3dyna.controller.benchmark;

import com.example.springmybatis3dyna.domain.Todo;
import com.example.springmybatis3dyna.domain.TodoJavaMapper;
import com.example.springmybatis3dyna.domain.TodoScriptMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/script/benchmark")
public class BenchmarkScriptController {

    @Autowired
    TodoScriptMapper todoMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final static int ITERATIONS = 100000;

    @Data
    static class Benchmark {
        String version = "MyBatis3Script#" + ITERATIONS;
        long findAll;
        long findBy;
        long inserts;
        long updates;
        long deletes;
    }

    @GetMapping
    public Benchmark benchmark() {
        jdbcTemplate.execute("drop table if exists todo;");
        jdbcTemplate.execute("create table if not exists todo (\n" +
                "    todo_id serial primary key,\n" +
                "    todo_title varchar(30),\n" +
                "    finished boolean,\n" +
                "    created_at timestamp\n" +
                ");");

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
        System.out.println("[MyBatis3Script][" + ITERATIONS + "]Insert Time: " + insertsTime.getTotalTimeMillis());

        StopWatch findAllTime = new StopWatch("findAll");
        findAllTime.start();
        for (int i = 1; i <= ITERATIONS; i++) {
            todoMapper.findAll();
        }
        findAllTime.stop();
        System.out.println("[MyBatis3Script][" + ITERATIONS + "]FindAll Time: " + findAllTime.getTotalTimeMillis());

        List<Todo> todos = new ArrayList<>(ITERATIONS);
        StopWatch findByTime = new StopWatch("findBy");
        findByTime.start();
        for (int i = 1; i <= ITERATIONS; i++) {
            todos.add(todoMapper.findById(i));
        }
        findByTime.stop();
        System.out.println("[MyBatis3Script][" + ITERATIONS + "]FindBy Time: " + findByTime.getTotalTimeMillis());

        StopWatch updatesTime = new StopWatch("updates");
        updatesTime.start();
        todos.stream().forEach( todo -> {
                    todo.setTodoTitle(todo.getTodoTitle()+"M");
                    todoMapper.update(todo);
                }
        );
        updatesTime.stop();
        System.out.println("[MyBatis3Script][" + ITERATIONS + "]Updates Time: " + updatesTime.getTotalTimeMillis());

        StopWatch deletesTime = new StopWatch("deletes");
        deletesTime.start();
        todos.stream().forEach( todo -> {
                    todoMapper.delete(todo.getTodoId());
                }
        );
        deletesTime.stop();
        System.out.println("[MyBatis3Script][" + ITERATIONS + "]Delete Time: " + deletesTime.getTotalTimeMillis());

        Benchmark benchmark = new Benchmark();
        benchmark.findBy = findByTime.getTotalTimeMillis();
        benchmark.findAll = findAllTime.getTotalTimeMillis();
        benchmark.inserts = insertsTime.getTotalTimeMillis();
        benchmark.updates = updatesTime.getTotalTimeMillis();
        benchmark.deletes = deletesTime.getTotalTimeMillis();

        return benchmark;
    }

}