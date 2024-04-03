

package com.example.springmybatis3dyna.domain;

import com.example.springmybatis3dyna.DBTestConfig;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@DataJdbcTest
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES
        , refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD
        , provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY
)
@ContextConfiguration(classes = {
        DBTestConfig.class//,
        //SqlSessionFactory.class
})
@Sql(scripts = {
        "classpath:schema.sql",
        "classpath:data.sql"
})
public class TodoJavaMapperTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected DataSource dataSource;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @SneakyThrows
    @Test
    void givenARecord_whenSelect_returnsTheRecord() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TodoJavaMapper todoJavaMapper = session.getMapper(TodoJavaMapper.class);

            Iterable<Todo> todos = todoJavaMapper.findAll();
            System.out.println(todos);

            long count = todoJavaMapper.count();
            System.out.println(count);

            Todo todo = new Todo();
            //todo.setTodoId(1);
            todo.setTodoTitle("Title");
            todo.setFinished(true);
            todo.setCreatedAt(LocalDateTime.now());

            todoJavaMapper.insert(todo);
            System.out.println(todo);

            Todo todoRet2 = todoJavaMapper.findById(2);
            System.out.println(todoRet2);
        }
    }
}
