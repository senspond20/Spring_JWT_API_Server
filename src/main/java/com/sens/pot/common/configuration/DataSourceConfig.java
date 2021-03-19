package com.sens.pot.common.configuration;
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
    private final boolean IS_USE_LOG4JDBC = true;
    private final boolean IS_AUTOCOMMIT = false;

    @Bean(name="dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        final String DB_USER = "senspi";
        final String DB_PWD = "ad0c5c677b5e4baee346419c8d51d613a15015ddcb023307fd3ed95f006ed093";
        final String DB_URL = "senspond.iptime.org:3309/PI_API_V1?useSsl=false&serverTimezone=UTC";
        /*
          CREATE DATABASE PI_API_V1 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci; 
          create user 'senspi'@'%' identified by 'ad0c5c677b5e4baee346419c8d51d613a15015ddcb023307fd3ed95f006ed093';
          grant all privileges on PI_API_V1.* to 'senspi'@'%';
          flush PRIVILEGES;
          SELECT SHA2('wldus961', 256);
        */
        HikariConfig config = new HikariConfig();

        if(IS_USE_LOG4JDBC){
            config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
            config.setJdbcUrl("jdbc:log4jdbc:mysql://" + DB_URL);    
        }else{
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://" + DB_URL);
        }
    
        config.setUsername(DB_USER);
        config.setPassword(DB_PWD);

        config.setAutoCommit(IS_AUTOCOMMIT); // autocommit 
        //config.setConnectionInitSql("SELECT 1");
        config.setPoolName("springHikariCP");
        config.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        config.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        config.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

    
        return new HikariDataSource(config);
    }



}
