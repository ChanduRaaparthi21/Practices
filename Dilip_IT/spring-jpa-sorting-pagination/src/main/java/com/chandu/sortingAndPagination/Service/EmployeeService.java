package com.chandu.sortingAndPagination.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.chandu.sortingAndPagination.Repository.EmployeeRepo;
import com.chandu.sortingAndPagination.entity.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	public void findAllEmployeesByAgeDesc() {
		
		List<Employee> aallEmployees = employeeRepo.findAll(Sort.by(Direction.DESC, "eage"));
		aallEmployees.forEach(System.out::println);
		
	}
	
public void findAllEmployeesByAgeAsc() {
		List<Employee> aallEmployees = employeeRepo.findAll(Sort.by(Direction.ASC, "eage"));
		aallEmployees.forEach(System.out::println);
	}


public void findAllEmployeesByEid() {
	List<Employee> emp = employeeRepo.findAll(Sort.by(Direction.ASC, "eid"));
	emp.forEach(System.out::println);
}

public void findAllEmployeesByNameByAsc() {
	List<Employee> emp = employeeRepo.findAll(Sort.by(Direction.ASC,"ename"));
	emp.forEach(System.out::println);
}

public void findAllEmpByCityAndAgeByDes() {
	List<Employee> emp = employeeRepo.findAll(Sort.by(Direction.DESC, "ecity","eage"));
	emp.forEach(System.out::println);
}

}
