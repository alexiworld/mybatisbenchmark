package com.example.springmybatis2.domain;

import com.example.springmybatis2.DBTestConfig;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
//@DataJdbcTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJdbc
@AutoConfigureTestDatabase
@ImportAutoConfiguration
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES
        , refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD
        , provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY
)
@ContextConfiguration(classes = {
        DBTestConfig.class,
        TodoMapper.class
})
@Sql(scripts = {
        "classpath:schema.sql",
        "classpath:data.sql"
})
public class TodoMapperTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected TodoMapper todoMapper;

    @PostConstruct
    @SneakyThrows
    void before() {
        //assertThat(AopUtils.getTargetClass(todoRepository)).isEqualTo(TodoRepository.class);
    }

    @SneakyThrows
    @Test
    void givenARecord_whenSelect_returnsTheRecord() {
        Iterable<Todo> todos = todoMapper.findAll();
        System.out.println(todos);

        long count = todoMapper.count();
        System.out.println(count);

        Todo todo = new Todo();
        //todo.setTodoId(1);
        todo.setTodoTitle("Title");
        todo.setFinished(true);
        todo.setCreatedAt(LocalDateTime.now());

        Todo todoRet = todoMapper.insert(todo);
        System.out.println(todoRet);

        Optional<Todo> todoRet2 = todoMapper.findById(2);
        System.out.println(todoRet2);
    }
}

