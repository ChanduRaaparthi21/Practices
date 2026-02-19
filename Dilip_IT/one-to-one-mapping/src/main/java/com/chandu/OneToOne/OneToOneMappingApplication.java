package com.chandu.OneToOne;
import com.chandu.OneToOne.Service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.chandu.OneToOne.Entity.Student;

@SpringBootApplication
public class OneToOneMappingApplication {

  

	public static void main(String[] args) {
	  ConfigurableApplicationContext applicationContext = SpringApplication.run(OneToOneMappingApplication.class, args);
	StudentService s =   applicationContext.getBean(StudentService.class);
	s.addStudent();
	
	  
	}

}
