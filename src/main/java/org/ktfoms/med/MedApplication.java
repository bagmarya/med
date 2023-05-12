package org.ktfoms.med;

import jakarta.persistence.EntityManagerFactory;


import java.util.Properties;
import javax.sql.DataSource;
import javax.sql.XAConnection;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;


@SpringBootApplication (scanBasePackages = "org.ktfoms.med")
@EnableAutoConfiguration(exclude = { //
        DataSourceAutoConfiguration.class, //
        DataSourceTransactionManagerAutoConfiguration.class, //
        HibernateJpaAutoConfiguration.class })
public class MedApplication {


    private static Logger logger = LoggerFactory.getLogger(MedApplication.class);

    @Autowired
    private Environment env;


    public static void main(String[] args){
        SpringApplication.run(MedApplication.class, args);
    }


    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        logger.info("## getDataSource: " + dataSource);
        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("current_session_context_class",
                env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        // Fix Postgres JPA Error:
        // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
        // properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setPackagesToScan("org.ktfoms.med.entity");
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.afterPropertiesSet();
        logger.info("## getSessionFactory ##");
        return sessionFactoryBean.getObject();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }
    @Autowired
    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("current_session_context_class",
                env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("org.ktfoms.med.entity");
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(properties);
        factoryBean.afterPropertiesSet();

        return factoryBean.getNativeEntityManagerFactory();

    }
    @Bean
    public ResourceLoader createResourceLoader() {
        return new DefaultResourceLoader();
    }


//    @Bean
//    ResourceBundleViewResolver viewResolver1() {
//        ResourceBundleViewResolver res = new ResourceBundleViewResolver();
//        res.setOrder(1);
//        res.setBasename("views");
//        return res;
//    }


}
