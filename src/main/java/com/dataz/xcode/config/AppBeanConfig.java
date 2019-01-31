package com.dataz.xcode.config;

import com.dataz.xcode.contants.DataSourceEnum;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.concurrent.*;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-9:37
 */
@Configuration
@ConfigurationProperties(prefix = "datasource")
@Setter
@Getter
public class AppBeanConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    @Bean
    public ExecutorService executorService(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("kd-%d").build();
        return new ThreadPoolExecutor(5, 5, 0,
                                             TimeUnit.SECONDS, new LinkedBlockingDeque<>(), namedThreadFactory);
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource hds = new HikariDataSource();

        hds.setDriverClassName(driverClassName);
        hds.setJdbcUrl(url);
        hds.setUsername(username);
        hds.setPassword(password);

        hds.setMaximumPoolSize(2);
        hds.setMinimumIdle(2);
        hds.setConnectionTestQuery("select 1 from dual");

        return hds;
    }

    @Bean
    public DataSourceEnum dataSourceEnum(){
        if (driverClassName.contains(DataSourceEnum.MYSQL.name().toLowerCase())){
            return DataSourceEnum.MYSQL;
        }else if (driverClassName.contains(DataSourceEnum.ORACLE.name().toLowerCase())){
            return DataSourceEnum.ORACLE;
        }

        return null;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

}
