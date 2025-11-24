package com.chandu.product.Repository;

import com.chandu.product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {

    public List<Product> findByName(String name);
    public List<Product> findByPriceGreaterThan(Double price);
    public List<Product> findByPriceLessThan(Double price);
    public List<Product> findByPrice(Double price);



}
