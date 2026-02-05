package com.chandu.sortingAndPagination.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandu.sortingAndPagination.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}
