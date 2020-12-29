package com.hdekker.security.users;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( 
		entityManagerFactoryRef = "userEntityManagerFactory",
		transactionManagerRef =  "userTransactionManager",
		basePackages = { "com.hdekker.security.users.repo" }
)
public class UserDatabase {

	  @Primary
	  @Bean(name = "userDataSource")
	  @ConfigurationProperties(prefix = "users.datasource")
	  public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	  }
	  
	  protected Map<String, Object> jpaProperties() {
		    Map<String, Object> props = new HashMap<>();
		    props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		    props.put("hibernate.hbm2ddl.auto", "update");
		    return props; 
		}
	  
	  @Primary
	  @Bean(name = "userEntityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean  
	  userEntityManagerFactory(
	    EntityManagerFactoryBuilder builder, 
	    @Qualifier("userDataSource") DataSource dataSource
	  ) { 
	    return builder 
	      .dataSource(dataSource)
	      .packages("com.hdekker.security.users.data")
	      .persistenceUnit("users") 
	      .properties(jpaProperties())
	      .build();
	  }
	  
	  @Primary
	  @Bean(name = "userTransactionManager")
	  public PlatformTransactionManager transactionManager(
	    @Qualifier("userEntityManagerFactory") EntityManagerFactory 
	    entityManagerFactory
	  ) {
	    return new JpaTransactionManager(entityManagerFactory);
	  }
	
}
