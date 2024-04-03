package com.example.springmybatis3dyna.controller.todo;

import com.example.springmybatis3dyna.domain.Todo;
import com.example.springmybatis3dyna.domain.TodoXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/v1/xmlmapper/todo")
public class TodoXmlController {

    @Autowired
    TodoXmlMapper todoXmlMapper;

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo) {
        todoXmlMapper.insert(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todo.setTodoId(id);
        todoXmlMapper.update(todo);
        return todo;
    }

    @GetMapping("/")
    public List<Todo> getTodos() {
        Iterable<Todo> todos = todoXmlMapper.findAll();
        List<Todo> todosRet = StreamSupport.stream(todos.spliterator(), false).toList();
        return todosRet;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id) {
        Todo todoRet = todoXmlMapper.findById(id);
        return todoRet;
    }

    @GetMapping("/count")
    public long getCount() {
        long count = todoXmlMapper.count();
        return count;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id) {
        todoXmlMapper.delete(id);
    }

}