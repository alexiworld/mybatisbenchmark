package com.example.springmybatis3dyna.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import java.time.LocalDateTime;

public class TodoDynamicSqlSupport {

    public static final TodoTable todoTable = new TodoTable();
    public static final SqlColumn<Integer> todoId = todoTable.todoId;
    public static final SqlColumn<String> todoTitle = todoTable.todoTitle;

    public static final SqlColumn<Boolean> finished = todoTable.finished;
    public static final SqlColumn<LocalDateTime> createdAt = todoTable.createdAt;

    public static final class TodoTable extends SqlTable {
        public final SqlColumn<Integer> todoId = column("todo_id");
        public final SqlColumn<String> todoTitle = column("todo_title");

        public final SqlColumn<Boolean> finished = column("finished");
        public final SqlColumn<LocalDateTime> createdAt = column("created_at");

        public TodoTable() {
            super("todo");
        }
    }

}
