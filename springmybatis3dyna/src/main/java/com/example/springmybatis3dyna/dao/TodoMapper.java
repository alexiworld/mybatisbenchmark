package com.example.springmybatis3dyna.dao;

import com.example.springmybatis3dyna.domain.Todo;
import org.apache.ibatis.annotations.*;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;

public interface TodoMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id = "todosRes", value = {
            @Result(property="todoId", column="todo_id"),
            @Result(property="todoTitle", column="todo_title"),
            @Result(property="finished", column="finished"),
            @Result(property="createdAt", column="created_at")
    })
    List<Todo> findAll(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("todosRes")
    Todo findById(SelectStatementProvider selectStatement);

    @InsertProvider(type= SqlProviderAdapter.class, method="insert")
    @Options(useGeneratedKeys=true, keyProperty="row.todoId")
    int insert(InsertStatementProvider<Todo> insertStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

}
