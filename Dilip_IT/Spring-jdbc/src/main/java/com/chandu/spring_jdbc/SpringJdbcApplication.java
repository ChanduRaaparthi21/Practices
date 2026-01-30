package com.chandu.spring_jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.chandu.spring_jdbc.DatabaseOperartions.DatabaseOperartions;

@SpringBootApplication
public class SpringJdbcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(SpringJdbcApplication.class, args);
		
		DatabaseOperartions databaseOperartions	= context.getBean(DatabaseOperartions.class);
		databaseOperartions.addProduct();
	}

}
