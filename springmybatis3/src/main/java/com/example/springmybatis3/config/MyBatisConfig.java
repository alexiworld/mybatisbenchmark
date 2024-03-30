package com.example.springmybatis3.config;

import com.example.springmybatis3.domain.TodoJavaMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
// Learning from https://mybatis.org/spring/getting-started.html, https://mybatis.org/mybatis-3/getting-started.html
@EnableJdbcRepositories({"com.example", "com.example"})
@EntityScan({"com.example", "com.example"})
@Import(MyBatisJdbcConfiguration.class)
public class MyBatisConfig implements WebMvcConfigurer {

    static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    static final String CONNECTION_TEST_QUERY = "SELECT 1";
    static final int IDLE_TIMEOUT = 60 * 1000;
    static final int VALIDATION_TIMEOUT = 15000;
    static final int MAX_LIFE_TIME = 60 * 1000;
    static final int LEAK_DETECTION_THRESHOLD = 30 * 1000;
    static final String DATASOURCE_PRIMARY = "primary";

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("schema.sql")
//                .addScript("data.sql")
//                .build();
//    }

    @Primary
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?application_name=mybatis");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("password");
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setConnectionTestQuery(CONNECTION_TEST_QUERY);
        hikariConfig.setIdleTimeout(IDLE_TIMEOUT);
        hikariConfig.setValidationTimeout(VALIDATION_TIMEOUT);
        hikariConfig.setMaxLifetime(MAX_LIFE_TIME);
        hikariConfig.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);
        hikariConfig.setPoolName(DATASOURCE_PRIMARY);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public DefaultTransactionDefinition defaultTxDefinition() {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setTimeout(5000);
        return defaultTransactionDefinition;
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        factoryBean.setMapperLocations(
                new ClassPathResource("com/example/springmybatis3/domain/TodoMapper.xml"));
        factoryBean.setDataSource(dataSource);
        factoryBean.getObject().getConfiguration().addMapper(TodoJavaMapper.class);
        return factoryBean.getObject();
    }

}