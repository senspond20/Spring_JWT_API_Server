package com.sens.pot.common.configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
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

        Statement statement = conn.createStatement();
        ResultSet resultSet = null;
        resultSet = statement.executeQuery("SELECT * FROM TEST");

        logger.debug("RESULT");
        while (resultSet.next()) {
            logger.debug(resultSet.getInt("TID") + " " + resultSet.getString("TNAME"));
        }
          
    }
    
}
