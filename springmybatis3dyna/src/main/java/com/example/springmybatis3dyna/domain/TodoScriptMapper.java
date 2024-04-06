package com.example.springmybatis3dyna.domain;

import org.apache.ibatis.annotations.*;

import java.util.Collection;

@Mapper
public interface TodoScriptMapper {

    @Select("""
            <script>
            SELECT todo_id, 
                <if test='true != true'>todo_title</if>
                <if test='true == true'>concat(todo_title, '*S') as todo_title</if>
                , finished, created_at
            FROM todo WHERE 1=1
                <if test='id != null and id gt 0'>AND todo_id = #{id}</if> 
                <if test='id == null'></if>
            </script>
            """)
    @Results(id = "todoRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    Todo findById(@Param("id") Integer id);

    @Select("""
            <script>
            SELECT todo_id, 
                <if test='true != true'>todo_title</if>
                <if test='true == true'>concat(todo_title, '*A') as todo_title</if>
                , finished, created_at 
            FROM todo LIMIT 5
            </script>
            """)
    @Results(id = "todosRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    Collection<Todo> findAll();

    @Insert("""
            <script>
            INSERT INTO todo (todo_title, finished, created_at) VALUES (        
                <if test='true != true'>#{todoTitle}</if>
                <if test='true == true'>concat(#{todoTitle}, '*I')</if>
                , #{finished}, #{createdAt}
            )
            </script>
            """)
    @Options(useGeneratedKeys = true, keyProperty = "todoId")
    void insert(Todo todo);

    @Update("""
            <script>
            UPDATE todo SET
            <if test='true != true'>todo_title = #{todoTitle}</if>
            <if test='true == true'>todo_title = concat(#{todoTitle}, '*U')</if> 
            , finished = #{finished}, created_at = #{createdAt} WHERE 1=1
                <if test='todoId != null and todoId gt 0'>AND todo_id = #{todoId}</if> 
                <if test='todoId == null'></if>
            </script>
            """)
    void update(Todo todo);

    @Delete("""
            <script>
            DELETE FROM todo WHERE 1=1
                <if test='id != null and id gt 0'>AND todo_id = #{id}</if> 
                <if test='id == null'></if>
            </script>
            """)
    void delete(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM todo WHERE finished = 'FALSE'")
    long count();

}