package com.sens.pot.common.configuration;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/*
    CREATE DATABASE PI_API_V1 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci; 
    create user 'senspi'@'%' identified by 'ad0c5c677b5e4baee346419c8d51d613a15015ddcb023307fd3ed95f006ed093';
    grant all privileges on PI_API_V1.* to 'senspi'@'%';
    flush PRIVILEGES;
    SELECT SHA2('wldus961', 256);
*/

@Configuration
@PropertySource("classpath:database.properties")
public class DataSourceConfig {
    
    @Value("${datasource.driverClassName}")
    private String driverClassName;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

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

    @Bean(name="dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource HikariConfigDataSource(){
      
        HikariConfig config = new HikariConfig();
        config.setDataSource(basicDataSource());
        // config.setAutoCommit(false);
        config.setPoolName("springHikariCP");
        config.setMaximumPoolSize(10);
        config.setMaximumPoolSize(30);
        config.setConnectionTimeout(1000);
        // config.setConnectionInitSql("SELECT 1");
        config.getConnectionTestQuery();

        // config.setLeakDetectionThreshold(2000);
        
        return new HikariDataSource(config);
    }


/*
    private final boolean IS_USE_LOG4JDBC = true;
    private static final boolean IS_USE_CLEAR = true;
    private static final boolean IS_USE_INIT_SCHEMA = true;
    private static final boolean IS_USE_INIT_DATA = true;

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

    }*/

  





}
