package com.example.springmybatis3dyna.controller.todo;

import com.example.springmybatis3dyna.domain.Todo;
import com.example.springmybatis3dyna.domain.TodoJavaMapper;
import com.example.springmybatis3dyna.domain.TodoScriptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/v1/scriptmapper/todo")
public class TodoScriptController {

    @Autowired
    TodoScriptMapper todoScriptMapper;

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo) {
        todoScriptMapper.insert(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todo.setTodoId(id);
        todoScriptMapper.update(todo);
        return todo;
    }

    @GetMapping("/")
    public List<Todo> getTodos() {
        Iterable<Todo> todos = todoScriptMapper.findAll();
        List<Todo> todosRet = StreamSupport.stream(todos.spliterator(), false).toList();
        return todosRet;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id) {
        Todo todoRet = todoScriptMapper.findById(id);
        return todoRet;
    }

    @GetMapping("/count")
    public long getCount() {
        long count = todoScriptMapper.count();
        return count;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id) {
        todoScriptMapper.delete(id);
    }

}