package com.example.springmybatis2.domain;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = false)
// Breakpoints can be set on org.springframework.orm.ibatis.SqlMapClientTemplate.execute
// and com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate.insert (to learn how to get
// generated ID back using nested selectKey statement) methods.
public class TodoMapper extends SqlMapClientDaoSupport {

    @Autowired
    public TodoMapper(@Qualifier(value = "sqlMapClient") SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    public Optional<Todo> findById(Integer id) {
        Todo todo = (Todo) getSqlMapClientTemplate().queryForObject(
                "com.example.springmybatis2.domain.TodoMapper.findById", id);
        return Optional.ofNullable(todo);
    }

    public Optional<Todo> findById() {
        Todo todo = (Todo) getSqlMapClientTemplate().queryForObject(
                "com.example.springmybatis2.domain.TodoMapper.findById");
        return Optional.ofNullable(todo);
    }

    public List<Todo> findAll() {
        List<Todo> todos = getSqlMapClientTemplate().queryForList(
                "com.example.springmybatis2.domain.TodoMapper.findAll");
        return todos;
    }

    // Sources:
    // Look for leonidv's comment at https://stackoverflow.com/questions/1769688/howto-return-ids-on-inserts-with-ibatis-with-returning-keyword
    // https://ibatis.apache.org/docs/dotnet/datamapper/ch03s03.html
    public Todo insertAsStatement(Todo todo) { // it works,
        Integer id = (Integer) getSqlMapClientTemplate().queryForObject(
                "com.example.springmybatis2.domain.TodoMapper.insertStatement", todo);
        todo.setTodoId(id);
        return todo;
    }

    public Todo insert(Todo todo) {
        //MyBatis2 already injected the ID into the bean.
        //Integer id = (Integer) getSqlMapClientTemplate().insert(
        //        "com.example.springmybatis2.domain.TodoMapper.insert", todo);
        //todo.setTodoId(id);
        getSqlMapClientTemplate().insert(
                "com.example.springmybatis2.domain.TodoMapper.insert", todo);
        return todo;
    }

    public Todo update(Todo todo) {
        int res = getSqlMapClientTemplate().update(
                "com.example.springmybatis2.domain.TodoMapper.update", todo);
        return todo;
    }

    public boolean deleteById(Integer id) {
        int res = getSqlMapClientTemplate().delete("com.example.springmybatis2.domain.TodoMapper.delete", id);
        return res > 0;
    }

    public long count() {
        Long count = (Long) getSqlMapClientTemplate().queryForObject(
                "com.example.springmybatis2.domain.TodoMapper.count");
        return count;
    }

    public void init() {
        getSqlMapClientTemplate().delete("com.example.springmybatis2.domain.TodoMapper.init");
    }

}
