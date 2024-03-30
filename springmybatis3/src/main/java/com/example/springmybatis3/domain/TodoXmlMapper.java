package com.example.springmybatis3.domain;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = false)
public class TodoXmlMapper {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public Todo findById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Todo todo = (Todo) session.selectOne(
                    "com.example.springmybatis3.domain.TodoMapper.findById", id);
            return todo;
        }
    }

    public List<Todo> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Todo> todos = session.selectList(
                    "com.example.springmybatis3.domain.TodoMapper.findAll");
            return todos;
        }
    }

    // Sources:
    // Look for leonidv's comment at https://stackoverflow.com/questions/1769688/howto-return-ids-on-inserts-with-ibatis-with-returning-keyword
    // https://ibatis.apache.org/docs/dotnet/datamapper/ch03s03.html
    public Todo insertAsStatement(Todo todo) { // it works,
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Integer id = (Integer) session.selectOne(
                    "com.example.springmybatis3.domain.TodoMapper.insertStatement", todo);
            todo.setTodoId(id);
            return todo;
        }
    }

    public Todo insert(Todo todo) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert(
                "com.example.springmybatis3.domain.TodoMapper.insert", todo);
            return todo;
        }
    }

    public Todo update(Todo todo) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int res = session.update(
                "com.example.springmybatis3.domain.TodoMapper.update", todo);
            return todo;
        }
    }

    public boolean delete(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int res = session.delete("com.example.springmybatis3.domain.TodoMapper.delete", id);
            return res > 0;
        }
    }

    public long count() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Long count = (Long) session.selectOne(
                    "com.example.springmybatis3.domain.TodoMapper.count");
            return count;
        }
    }

}
