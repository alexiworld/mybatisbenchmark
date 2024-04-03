package com.example.springmybatis3dyna.domain;

import org.apache.ibatis.annotations.*;

import java.util.Collection;

@Mapper
public interface TodoJavaMapper {

    @Select("SELECT todo_id, todo_title, finished, created_at FROM todo WHERE todo_id = #{id}")
    @Results(id = "todoRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    Todo findById(@Param("id") Integer id);

    @Select("SELECT todo_id, todo_title, finished, created_at FROM todo LIMIT 5")
    @Results(id = "todosRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    Collection<Todo> findAll();

    @Insert("INSERT INTO todo (todo_title, finished, created_at) VALUES (#{todoTitle}, #{finished}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "todoId")
    void insert(Todo todo);

    @Update("UPDATE todo SET todo_title = #{todoTitle}, finished = #{finished}, created_at = #{createdAt} WHERE todo_id = #{todoId}")
    void update(Todo todo);

    @Delete("DELETE FROM todo WHERE todo_id = #{id}")
    void delete(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM todo WHERE finished = 'FALSE'")
    long count();

}