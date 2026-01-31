package com.chandu.Spring_data_jpa;

import com.chandu.Spring_data_jpa.Service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.chandu.Spring_data_jpa.Service.ProductService;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
	ConfigurableApplicationContext context =	SpringApplication.run(SpringDataJpaApplication.class, args);
	
	ProductService productService=  context.getBean(ProductService.class);
	productService.addProduct();
	
	
	}

}
