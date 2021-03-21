package com.sens.pot.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class InitSql {
    
    final boolean IS_USE_INIT_SCHEMA = true;
    final boolean IS_USE_INIT_DATA = true;
    final boolean IS_USE_LOG4JDBC = true;
 
    private String driverClassName;
    private String url;
    private String username;
    private String password;


    @BeforeEach
    void getValues(){

        String path = Paths.get(System.getProperty("user.dir"), "src/main/resources","database.properties").toString();
        Properties properties = new Properties();
    // Read properties file.
        try(FileInputStream fis = new FileInputStream(path)){
            properties.load(fis);
            fis.close();
        } catch (IOException e) {}

        driverClassName = properties.getProperty("datasource.driverClassName");
        url = properties.getProperty("datasource.url");
        username = properties.getProperty("datasource.username");
        password = properties.getProperty("datasource.password");
    }

    private final DataSource basicDataSource(){

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        DataSource dataSource = dataSourceBuilder.type(HikariDataSource.class)
                                                .driverClassName(driverClassName)
                                                .url(url)
                                                .username(username)  
                                                .password(password)
                                                .build();
        return dataSource;
    }


    @Test
    void insert_Init_Sql(){
        // Resource initSchema = new ClassPathResource("sql/schema-v1.sql");
        // Resource initData = new ClassPathResource("sql/data-v1.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

        // if(IS_USE_INIT_SCHEMA)  databasePopulator.addScript(initSchema);
        // if(IS_USE_INIT_DATA)    databasePopulator.addScript(initData);
        databasePopulator.addScript(new ClassPathResource("sql/account.sql"));
        databasePopulator.setIgnoreFailedDrops(true);
        // databasePopulator.setSqlScriptEncoding("utf8");
        databasePopulator.execute(basicDataSource());

    }
}
