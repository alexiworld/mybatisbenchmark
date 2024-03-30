package com.example.springmybatis3jdbc.domain;

import com.example.springmybatis3jdbc.DBTestConfig;
import com.example.springmybatis3jdbc.repository.TodoRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;

@DataJdbcTest
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES
        , refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD
        , provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY
)
@ContextConfiguration(classes = {
        DBTestConfig.class,
        MyBatisJdbcConfiguration.class,
        TodoRepository.class,
        TodoMapper.class
})
@Sql(scripts = {
        "classpath:schema.sql",
        "classpath:data.sql"
})
//@Import(MyBatisJdbcConfiguration.class)
public class TodoMapperTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected TodoRepository todoRepository;

    @PostConstruct
    @SneakyThrows
    void before() {
        //assertThat(AopUtils.getTargetClass(todoRepository)).isEqualTo(TodoRepository.class);
    }

    @SneakyThrows
    @Test
    void givenARecord_whenSelect_returnsTheRecord() {
        Iterable<Todo> todos = todoRepository.findAll();
        System.out.println(todos);

        long count = todoRepository.count();
        System.out.println(count);

        Todo todo = new Todo();
        //todo.setTodoId(1);
        todo.setTodoTitle("Title");
        todo.setFinished(true);
        todo.setCreatedAt(LocalDateTime.now());

        Todo todoRet = todoRepository.save(todo);
        System.out.println(todoRet);

        Optional<Todo> todoRet2 = todoRepository.findById(2);
        System.out.println(todoRet2);
    }
}

