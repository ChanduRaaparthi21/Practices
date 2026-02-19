package com.chandu.OneToOne.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandu.OneToOne.Entity.Student;

public interface StudentRepo extends JpaRepository<Student,Integer> {

}
