package com.sens.pot.Configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
public class DataConectionTest {
    private final DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(DataConectionTest.class);

    @Test
    public void test() throws Exception {
        Connection conn = dataSource.getConnection();

        final DatabaseMetaData md = conn.getMetaData();
        logger.info("{}", md.getDriverName());
        logger.info("{}", md.getURL());
        logger.info("{}", md.getUserName());  

        Statement statement = conn.createStatement();
        ResultSet resultSet = null;
        resultSet = statement.executeQuery("SELECT * FROM TEST");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("TID") + " " + resultSet.getString("TNAME"));
        }
          
    } 
}
