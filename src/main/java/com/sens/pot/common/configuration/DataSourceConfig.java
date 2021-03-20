package com.sens.pot.common.configuration;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;

import javax.annotation.Resources;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataSourceConfig {
    
    /**
     * Config
     */
    private final boolean IS_USE_LOG4JDBC = true;
    private static final boolean IS_USE_CLEAR = true;
    private static final boolean IS_USE_INIT_SCHEMA = true;
    private static final boolean IS_USE_INIT_DATA = true;
  
    /*
        CREATE DATABASE PI_API_V1 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci; 
        create user 'senspi'@'%' identified by 'ad0c5c677b5e4baee346419c8d51d613a15015ddcb023307fd3ed95f006ed093';
        grant all privileges on PI_API_V1.* to 'senspi'@'%';
        flush PRIVILEGES;
        SELECT SHA2('wldus961', 256);
    */
    private final DataSource basicDataSource(){
        final String DB_USER = "senspi";
        final String DB_PWD = "ad0c5c677b5e4baee346419c8d51d613a15015ddcb023307fd3ed95f006ed093";
        final String DB_URL = "senspond.iptime.org:3309/PI_API_V1?useSsl=false&serverTimezone=UTC";
       
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:log4jdbc:mysql://" + DB_URL;
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        if(IS_USE_LOG4JDBC){
            driverClassName = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";
            jdbcUrl = "jdbc:log4jdbc:mysql://" + DB_URL;
        }
       
        DataSource dataSource = dataSourceBuilder.type(HikariDataSource.class)
                                                .driverClassName(driverClassName)
                                                .url(jdbcUrl)
                                                .username(DB_USER)  
                                                .password(DB_PWD)
                                                .build();
        return dataSource;
    }


    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    
        Resource initSchema = new ClassPathResource("sql/schema-v1.sql");
        Resource initData = new ClassPathResource("sql/data-v1.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

        if(IS_USE_INIT_SCHEMA)  databasePopulator.addScript(initSchema);
        if(IS_USE_INIT_DATA)    databasePopulator.addScript(initData);
        
        databasePopulator.setIgnoreFailedDrops(true);
        // databasePopulator.setSqlScriptEncoding("utf8");
        // databasePopulator.execute(dataSource);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);    
        // initializer.setDatabaseCleaner(databasePopulator);
        return initializer;

    }

    @Bean(name="dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource HikariConfigDataSource(){
      
        HikariConfig config = new HikariConfig();
        config.setDataSource(basicDataSource());
        // config.setAutoCommit(false);

        config.setPoolName("springHikariCP");
        config.setMaximumPoolSize(10);
        config.setMaximumPoolSize(30);
        config.setConnectionTimeout(1000);
        // config.setLeakDetectionThreshold(2000);
        // config.setConnectionInitSql("SELECT 1");
        
        return new HikariDataSource(config);
    }


  





}
