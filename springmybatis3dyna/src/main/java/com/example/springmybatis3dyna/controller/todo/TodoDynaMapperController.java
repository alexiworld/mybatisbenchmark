package com.example.springmybatis3dyna.controller.todo;

import com.example.springmybatis3dyna.dao.TodoDao;
import com.example.springmybatis3dyna.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/dynamapper/todo")
public class TodoDynaMapperController {

    @Autowired
    TodoDao todoDao;

    @PostMapping("/")
    public Todo saveTodo(@RequestBody Todo todo) {
        todoDao.insertUsingMapper(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todo.setTodoId(id);
        todoDao.updateUsingMapper(todo);
        return todo;
    }

    @GetMapping("/")
    public List<Todo> getTodos() {
        List<Todo> todosRet = todoDao.findAllUsingMapper();
        return todosRet;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id) {
        Todo todoRet = todoDao.findByIdUsingMapper(id);
        return todoRet;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id){
        todoDao.deleteUsingMapper(id);
    }

}