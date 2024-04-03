package com.example.springmybatis3dyna.dao;

import com.example.springmybatis3dyna.domain.Todo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.springmybatis3dyna.dao.TodoDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

// https://github.com/mybatis/mybatis-dynamic-sql/blob/759d8ecce2ff2d23fb40f54d9b96b40240198ba2/src/main/java/org/mybatis/dynamic/sql/util/mybatis3/MyBatis3Utils.java
// https://github.com/mybatis/mybatis-dynamic-sql/blob/759d8ecce2ff2d23fb40f54d9b96b40240198ba2/src/test/java/examples/generated/always/mybatis/GeneratedAlwaysMapper.java
// https://github.com/mybatis/mybatis-dynamic-sql/blob/master/src/test/java/examples/generated/always/spring/SpringTest.java
// https://github.com/mybatis/mybatis-dynamic-sql/blob/759d8ecce2ff2d23fb40f54d9b96b40240198ba2/src/test/java/examples/simple/PersonMapper.java
// https://mybatis.org/mybatis-dynamic-sql/docs/select.html
// https://mybatis.org/mybatis-dynamic-sql/docs/delete.html
// https://mybatis.org/mybatis-dynamic-sql/docs/update.html
// https://mybatis.org/mybatis-dynamic-sql/docs/insert.html
@Repository
public class TodoDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public Todo findByIdUsingMapper(Integer id) {
        SelectStatementProvider selectStatement = SqlBuilder.select(todoId, todoTitle, finished, createdAt)
                .from(todoTable)
                .where(todoId, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TodoMapper mapper = session.getMapper(TodoMapper.class);
            Todo todo = mapper.findById(selectStatement);
            return todo;
        }
    }

    public Todo findByIdUsingTemplate(Integer id) {
        SelectStatementProvider selectStatement = SqlBuilder.select(todoId, todoTitle, finished, createdAt)
                .from(todoTable)
                .where(todoId, isEqualTo(id))
                .build()
                .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        SqlParameterSource namedParameters = new MapSqlParameterSource(selectStatement.getParameters());

        List<Todo> records = template.query(selectStatement.getSelectStatement(), namedParameters,
                (rs, rowNum) -> {
                    Todo record = new Todo();
                    record.setTodoId(rs.getInt(1));
                    record.setTodoTitle(rs.getString(2));
                    record.setFinished(rs.getBoolean(3));
                    record.setCreatedAt(rs.getTimestamp(4).toLocalDateTime());
                    return record;
                });

        return records.get(0);
    }

    public List<Todo> findAllUsingMapper() {
        SelectStatementProvider selectStatement = SqlBuilder.select(todoId, todoTitle, finished, createdAt)
                .from(todoTable)
                .limit(5) // to be consistent with other experiments
                .build()
                .render(RenderingStrategies.MYBATIS3);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TodoMapper mapper = session.getMapper(TodoMapper.class);
            List<Todo> todos = mapper.findAll(selectStatement);
            return todos;
        }
    }

    public List<Todo> findAllUsingTemplate() {
        SelectStatementProvider selectStatement = SqlBuilder.select(todoId, todoTitle, finished, createdAt)
                .from(todoTable)
                .limit(5) // to be consistent with other experiments
                .build()
                .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        SqlParameterSource namedParameters = new MapSqlParameterSource(selectStatement.getParameters());

        List<Todo> records = template.query(selectStatement.getSelectStatement(), namedParameters,
                (rs, rowNum) -> {
                    Todo record = new Todo();
                    record.setTodoId(rs.getInt(1));
                    record.setTodoTitle(rs.getString(2));
                    record.setFinished(rs.getBoolean(3));
                    record.setCreatedAt(rs.getTimestamp(4).toLocalDateTime());
                    return record;
                });

        return records;
    }

    public Todo insertUsingMapper(Todo todo){
        InsertStatementProvider<Todo> insertStatement = SqlBuilder.insert(todo)
                .into(todoTable)
                //.map(todoId).toProperty("todoId")
                .map(todoTitle).toProperty("todoTitle")
                .map(finished).toProperty("finished")
                .map(createdAt).toProperty("createdAt")
                .build()
                .render(RenderingStrategies.MYBATIS3);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TodoMapper mapper = session.getMapper(TodoMapper.class);
            int res = mapper.insert(insertStatement);
            // Unnecessary since Todo object's ID has been already set by MyBatis.
            // todo.setTodoId(insertStatement.getRow().getTodoId());
            return todo;
        }
    }

    public Todo insertUsingTemplate(Todo todo){
        InsertStatementProvider<Todo> insertStatement = SqlBuilder.insert(todo)
                .into(todoTable)
                //.map(todoId).toProperty("todoId")
                .map(todoTitle).toProperty("todoTitle")
                .map(finished).toProperty("finished")
                .map(createdAt).toProperty("createdAt")
                .build()
                .render(RenderingStrategies.SPRING_NAMED_PARAMETER);


        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(insertStatement);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = template.update(insertStatement.getInsertStatement(), parameterSource, keyHolder);
        Integer key = (Integer) keyHolder.getKeys().get("todo_id");

        todo.setTodoId(key);
        return todo;
    }

    public Todo updateUsingMapper(Todo todo) {
        UpdateStatementProvider updateStatement = update(todoTable)
                .set(todoTitle).equalTo(todo.getTodoTitle())
                .set(finished).equalTo(todo.isFinished())
                .set(createdAt).equalTo(todo.getCreatedAt())
                .where(todoId, isEqualToWhenPresent(todo.getTodoId()))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TodoMapper mapper = session.getMapper(TodoMapper.class);
            int rows = mapper.update(updateStatement);
            return todo;
        }
    }

    public Todo updateUsingTemplate(Todo todo) {
        UpdateStatementProvider updateStatement = update(todoTable)
                .set(todoTitle).equalTo(todo.getTodoTitle())
                .set(finished).equalTo(todo.isFinished())
                .set(createdAt).equalTo(todo.getCreatedAt())
                .where(todoId, isEqualToWhenPresent(todo.getTodoId()))
                .build()
                .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        SqlParameterSource parameterSource = new MapSqlParameterSource(updateStatement.getParameters());
        int rows = template.update(updateStatement.getUpdateStatement(), parameterSource);
        return todo;
    }

    public boolean deleteUsingMapper(Integer id) {
        DeleteStatementProvider deleteStatement = deleteFrom(todoTable)
                .where(todoId, isEqualToWhenPresent(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TodoMapper mapper = session.getMapper(TodoMapper.class);
            int rows = mapper.delete(deleteStatement);
            return rows > 0;
        }
    }

    public boolean deleteUsingTemplate(Integer id) {
        DeleteStatementProvider deleteStatement = deleteFrom(todoTable)
                .where(todoId, isEqualToWhenPresent(id))
                .build()
                .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        SqlParameterSource parameterSource = new MapSqlParameterSource(deleteStatement.getParameters());
        int rows = template.update(deleteStatement.getDeleteStatement(), parameterSource);
        return rows > 0;
    }

}
