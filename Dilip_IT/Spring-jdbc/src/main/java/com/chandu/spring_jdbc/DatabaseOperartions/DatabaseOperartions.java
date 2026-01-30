package com.chandu.spring_jdbc.DatabaseOperartions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseOperartions {
	
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public void addProduct() {
		

		jdbcTemplate.update("INSERT INTO product values (101,'samsung',40000)");

		
	}

}
