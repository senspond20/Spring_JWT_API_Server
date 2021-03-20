package com.sens.pot.common.configuration;

public class DataJpaConfig {
   // package com.sensweb.demo.config;

// import java.util.Properties;

// import javax.persistence.EntityManager;
// import javax.persistence.EntityManagerFactory;
// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
// import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Configuration
// @EnableJpaRepositories("com.sensweb.demo")
// @EnableTransactionManagement
// public class SpringDataJpaConfig {
    
//     @Autowired
//     private DataSource dataSource;
 
//     @Bean
//     public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//         return entityManagerFactory.createEntityManager();
//     }
//     @Bean
//     public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//         JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//         jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//         return jpaTransactionManager;
//     }

//     @Bean
//     public EntityManagerFactory entityManagerFactory() {
//         HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//         hibernateJpaVendorAdapter.setGenerateDdl(true); //  spring.jpa.generate-ddl=true
//         LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//         factory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
//         factory.setJpaProperties(properties());
//         factory.setPackagesToScan("com.sensweb.demo");
//         factory.setDataSource(dataSource);
//         factory.afterPropertiesSet();
//         return factory.getObject();
//     }
//     /**
//      * 
//      * @return
//      */
//     private Properties properties(){
//         Properties properties = new Properties();
    
//         properties.put("hibernate.show_sql", "true");    // spring.jpa.properties.hibernate.show_sql=true
//         properties.put("hibernate.format_sql","true");       // spring.jpa.properties.hibernate.format_sql=true
//         properties.put("hibernate.use_sql_comments", "true");  // spring.jpa.properties.hibernate.use_sql_comments=true
        
//         // # spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDB103Dialect
//         properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
//         // properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

//         properties.put("hibernate.hbm2ddl.auto", "create"); // spring.jpa.hibernate.ddl-auto=create-drop
//         return properties;
//     }

// } 
}
