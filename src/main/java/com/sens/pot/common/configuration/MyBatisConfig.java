package com.sens.pot.common.configuration;

import java.nio.file.Paths;

import javax.sql.DataSource;

import com.sens.pot.PotApplication;
import com.sens.pot.common.utils.FilesCopyUtils;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.sens.pot.web.repository")
public class MyBatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        Resource mybatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml");
		
		sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(mybatisConfig);
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		return sqlSessionFactoryBean.getObject();
	}

	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}