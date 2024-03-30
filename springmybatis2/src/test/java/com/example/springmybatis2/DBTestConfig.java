package com.example.springmybatis2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DBTestConfig {

    @Bean
    public SqlMapClientFactoryBean sqlMapClient(DataSource dataSource) {
        SqlMapClientFactoryBean sqlMapClientFactoryBean = new SqlMapClientFactoryBean();
        sqlMapClientFactoryBean.setConfigLocations(new Resource[]{
                new ClassPathResource("sqlMapConfig.xml")
        });
        sqlMapClientFactoryBean.setDataSource(dataSource);
        return sqlMapClientFactoryBean;
    }

}
