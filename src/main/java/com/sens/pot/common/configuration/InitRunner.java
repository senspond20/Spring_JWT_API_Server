package com.sens.pot.common.configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;

import com.sens.pot.web.domain.test.Test;
import com.sens.pot.web.repository.test.TestJpaRepository;
import com.sens.pot.web.repository.test.TestMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class InitRunner implements CommandLineRunner {

    private final TestMapper testMapper;
    private final TestJpaRepository testJPA;

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
