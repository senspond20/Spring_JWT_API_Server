package com.sens.pot.configuration;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    
    /**
     * Config
     */
    private final boolean USELOG4JDBC = false;
    private final boolean AUTOCOMMIT = false;

    @Bean(name="dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){

        /*
            CREATE DATABASE springweb DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci; 
            create user 'senspond'@'%' identified by 'dlrjs0326';
            grant all privileges on springweb.* to 'senspond'@'%';
            flush PRIVILEGES;
        */

        HikariConfig config = new HikariConfig();

        if(USELOG4JDBC){
            config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
            config.setJdbcUrl("jdbc:log4jdbc:mysql://senspond.iptime.org:3309/springweb?useSsl=false&serverTimezone=UTC");    
        }else{
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://senspond.iptime.org:3309/springweb?useSsl=false&serverTimezone=UTC");
        }
    
        config.setUsername("senspond");
        config.setPassword("dlrjs0326");

        config.setAutoCommit(AUTOCOMMIT); // autocommit 
        //config.setConnectionInitSql("SELECT 1");
        config.setPoolName("springHikariCP");
        config.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        config.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        config.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("dataSource.useServerPrepStmts", "true");


        return new HikariDataSource(config);
    }
}
