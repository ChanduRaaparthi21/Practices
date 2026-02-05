package com.chandu.sortingAndPagination;

import com.chandu.sortingAndPagination.Service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringJpaSortingPaginationApplication {

   
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringJpaSortingPaginationApplication.class, args);
		
		EmployeeService service = context.getBean(EmployeeService.class);
		//service.findAllEmployeesByAgeDesc();
		//service.findAllEmployeesByAgeAsc();
		//service.findAllEmployeesByEid();
//		service.findAllEmployeesByNameByAsc();
		service.findAllEmpByCityAndAgeByDes();
	}

}
