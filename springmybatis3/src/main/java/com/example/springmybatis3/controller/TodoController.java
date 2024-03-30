package com.example.springmybatis3.controller;

import com.example.springmybatis3.domain.Todo;
import com.example.springmybatis3.domain.TodoJavaMapper;
import com.example.springmybatis3.domain.TodoXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/v1/todo")
public class TodoController {

    @Autowired
    //TodoJavaMapper todoMapper;
    TodoXmlMapper todoMapper;

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo){
        todoMapper.insert(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todo.setTodoId(id);
        todoMapper.update(todo);
        return todo;
    }

    @GetMapping("/")
    public List<Todo> getTodos() {
        Iterable<Todo> todos = todoMapper.findAll();
        List<Todo> todosRet = StreamSupport.stream(todos.spliterator(), false).toList();
        return todosRet;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id){
        Todo todoRet = todoMapper.findById(id);
        return todoRet;
    }

    @GetMapping("/count")
    public long getCount() {
        long count = todoMapper.count();
        return count;
    }

    // Demonstrates fetching vehicle. The returned vehicle (if any found) must have all fields decrypted.
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id){
        todoMapper.delete(id);
    }

}