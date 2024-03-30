package com.example.springmybatis3jdbc.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.data.jdbc.mybatis.MyBatisContext;

import java.util.Collection;
import java.util.Optional;

@Mapper
public interface TodoMapper {

    @Select("SELECT todo_id, todo_title, finished, created_at FROM todo WHERE todo_id = #{id}")
    @Results(id = "todoRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    Optional<Todo> findById(MyBatisContext context);

    //@Select("SELECT 1 as todo_id, todo_title, finished, created_at FROM todo")
    @Select("SELECT todo_id, todo_title, finished, created_at FROM todo LIMIT 5")
    @Results(id = "todosRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    Collection<Todo> findAll();

    // Option#1: Calling the mapper directly
    // @Insert("INSERT INTO todo (todo_title, finished, created_at) VALUES (#{instance.todoTitle}, #{instance.finished}, #{instance.createdAt})")
    // @Options(useGeneratedKeys = true, keyProperty = "instance.todoId")
    // void insert(MyBatisContext context);

    @Insert("INSERT INTO todo (todo_title, finished, created_at) VALUES (#{instance.todoTitle}, #{instance.finished}, #{instance.createdAt})")
    //@Options(useGeneratedKeys = true, keyProperty = "row.id")
    //@Options(useGeneratedKeys = true, keyProperty = "instance.todoId")
    //@Options(useGeneratedKeys = true, keyProperty = "instance.todoId,id")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Todo insert(MyBatisContext context);

    //@Update("UPDATE todo SET todo_title = #{instance.todoTitle}, finished = #{instance.finished}, created_at = #{instance.createdAt} WHERE todo_id = #{id}")
    @Update("UPDATE todo SET todo_title = #{instance.todoTitle}, finished = #{instance.finished}, created_at = #{instance.createdAt} WHERE todo_id = #{instance.id}")
    Todo update(MyBatisContext context);

    @Delete("DELETE FROM todo WHERE todo_id = #{id}")
    void delete(MyBatisContext context);

    @Select("SELECT COUNT(*) FROM todo WHERE finished = 'FALSE'")
    //@Select("SELECT COUNT(*) FROM todo WHERE finished = 'f'")
    long count(MyBatisContext context);

}