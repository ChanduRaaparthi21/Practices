package com.chandu.OneToOne.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chandu.OneToOne.Entity.Address;
import com.chandu.OneToOne.Entity.Student;
import com.chandu.OneToOne.Repository.StudentRepo;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	
	public void addStudent() {
		
		Address ad = new Address();
		ad.setCity("hyd");
		ad.setState("tg");
		ad.setPincode("500000");
		
		
		Student s = new Student();
		s.setName("chandu");
		s.setEmail("chandu@gmail.com");
		s.setPhone("7777");
		s.setAddress(ad);
		
		studentRepo.save(s);
	}

}
