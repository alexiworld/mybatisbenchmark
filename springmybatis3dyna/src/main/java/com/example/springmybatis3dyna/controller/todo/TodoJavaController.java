package com.example.springmybatis3dyna.controller.todo;

import com.example.springmybatis3dyna.domain.Todo;
import com.example.springmybatis3dyna.domain.TodoJavaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/v1/javamapper/todo")
public class TodoJavaController {

    @Autowired
    TodoJavaMapper todoJavaMapper;

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo) {
        todoJavaMapper.insert(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todo.setTodoId(id);
        todoJavaMapper.update(todo);
        return todo;
    }

    @GetMapping("/")
    public List<Todo> getTodos() {
        Iterable<Todo> todos = todoJavaMapper.findAll();
        List<Todo> todosRet = StreamSupport.stream(todos.spliterator(), false).toList();
        return todosRet;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id) {
        Todo todoRet = todoJavaMapper.findById(id);
        return todoRet;
    }

    @GetMapping("/count")
    public long getCount() {
        long count = todoJavaMapper.count();
        return count;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id) {
        todoJavaMapper.delete(id);
    }

}