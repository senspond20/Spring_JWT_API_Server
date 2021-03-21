package com.sens.pot.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@SpringBootTest
public class InitSqlC {
    final boolean IS_USE_INIT_SCHEMA = false;
    final boolean IS_USE_INIT_DATA = true;
 
    @Autowired DataSource dataSource;
    
    @Test
    void insert_Init_Sql(){
        Resource initSchema = new ClassPathResource("sql/schema-v1.sql");
        Resource initData = new ClassPathResource("sql/data-v1.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

        if(IS_USE_INIT_SCHEMA)  databasePopulator.addScript(initSchema);
        if(IS_USE_INIT_DATA)    databasePopulator.addScript(initData);
        // databasePopulator.addScript(new ClassPathResource("sql/account.sql"));
        databasePopulator.setIgnoreFailedDrops(true);
        // databasePopulator.setSqlScriptEncoding("utf8");
        databasePopulator.execute(dataSource);

    }
}
