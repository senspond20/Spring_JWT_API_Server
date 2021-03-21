package com.sens.pot.common.configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
// @Component
public class InitRunner implements CommandLineRunner {

    private final DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(InitRunner.class);

 
    
    @Override
    public void run(String... args) throws Exception {

        Connection conn = dataSource.getConnection();

        final DatabaseMetaData md = conn.getMetaData();
        logger.info("{}", md.getDriverName());
        logger.info("{}", md.getURL());
        logger.info("{}", md.getUserName());  
        // select table_name from information_schema.TABLES where table_schema = database()
        // 테스트
        // Statement statement = conn.createStatement();
        // ResultSet resultSet = null;
        // resultSet = statement.executeQuery("SELECT * FROM TEST");
        
        // logger.debug("------- JDBC ------");
        // while (resultSet.next()) {
        //      logger.debug(resultSet.getInt("TID") + " " + resultSet.getString("TNAME"));
        // }
        // conn.close();


        // // Mybatis
        // logger.debug("------- MYBATIS ------");
        // List<Test> list = testMapper.selectTestAll();
        // System.out.println(list);


        // // JPA
        // logger.debug("------- JPA ------");
        // List<Test> list2 = testJPA.findAll();
        // System.out.println(list2);
    }
    
}
