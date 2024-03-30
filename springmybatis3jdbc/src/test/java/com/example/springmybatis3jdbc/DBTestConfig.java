package com.example.springmybatis3jdbc;

import com.example.springmybatis3jdbc.domain.TodoMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories({"com.example"})
@EntityScan({"com.example"})
public class DBTestConfig {

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        //factoryBean.setMapperLocations(
        //        new ClassPathResource("com/example/springmybatis3/domain/TodoMapper.xml"));
        factoryBean.setDataSource(dataSource);
        factoryBean.getObject().getConfiguration().addMapper(TodoMapper.class);
        return factoryBean.getObject();
    }

    @Bean
    @Autowired
    public SqlSession sqlSession(SqlSessionFactory factory) {
        return factory.openSession();
    }

}
