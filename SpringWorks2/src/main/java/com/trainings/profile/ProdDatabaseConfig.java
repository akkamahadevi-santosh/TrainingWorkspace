package com.trainings.profile;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


//Pro-MySQL

@Configuration
@Profile("Production")
public class ProdDatabaseConfig implements DatabaseConfig{
	
	@Bean
	public DataSource createDataSource() {
		
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	System.out.println("Creating Production Database Instance");
		return dataSource;
	}
	

}