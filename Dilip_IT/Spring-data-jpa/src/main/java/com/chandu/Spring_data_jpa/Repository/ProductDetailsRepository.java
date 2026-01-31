package com.chandu.Spring_data_jpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandu.Spring_data_jpa.Entity.Product;

public interface ProductDetailsRepository extends JpaRepository<Product,Integer> {

}
