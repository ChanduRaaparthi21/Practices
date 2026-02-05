package com.chandu.sortingAndPagination.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.chandu.sortingAndPagination.Repository.EmployeeRepo;
import com.chandu.sortingAndPagination.entity.Employee;

@Service
public class EmployeePagination {

	
	@Autowired
	EmployeeRepo employeeRepo;
	
	
	public void getEmployees() {
		Page<Employee> page = employeeRepo.findAll(Pageable.ofSize(5));
		List<Employee> emp = page.getContent();
		emp.forEach(System.out::println);	
	}
	
	public void getEmployeesByPageNoAndCount(int pageNo, int recordsPerPage) {
		
		List<Employee> emp = employeeRepo.findAll(PageRequest.of(pageNo, recordsPerPage)).getContent();
		emp.forEach(System.out::println);
		
	}
	
	
public void getEmployeesByPageNoAndCountAndSorting(int pageNo, int recordsPerPage) {
		
		List<Employee> emp = employeeRepo.findAll(PageRequest.of(pageNo, recordsPerPage,Sort.by(Direction.DESC,"eage"))).getContent();
		emp.forEach(System.out::println);
		
	}
	
	
}
