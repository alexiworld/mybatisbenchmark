package com.example.springmybatis3jdbc.controller;

import com.example.springmybatis3jdbc.domain.Todo;
import com.example.springmybatis3jdbc.domain.TodoMapper;
import com.example.springmybatis3jdbc.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/v1/todo")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoMapper todoMapper;

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo){
        // Option#1
        //MyBatisContext myBatisContext = new MyBatisContext(null, todo, Todo.class);
        //todoMapper.insert(myBatisContext);
        //return todo;

        // Option#2
        Todo todoRet = todoRepository.save(todo);
        return todoRet;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todo.setTodoId(id);
        Todo todoRet = todoRepository.save(todo);
        return todoRet;
    }

    @GetMapping("/")
    public List<Todo> getTodos() {
        Iterable<Todo> todos = todoRepository.findAll();
        List<Todo> todosRet = StreamSupport.stream(todos.spliterator(), false).toList();
        return todosRet;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id){
        Todo todoRet = todoRepository.findById(id).get();
        return todoRet;
    }

    @GetMapping("/count")
    public long getCount() {
        long count = todoRepository.count();
        return count;
    }

    // Demonstrates fetching vehicle. The returned vehicle (if any found) must have all fields decrypted.
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id){
        todoRepository.deleteById(id);
    }


}