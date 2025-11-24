package com.chandu.product.Service;

import com.chandu.product.Entity.Product;
import com.chandu.product.Exception.DuplicateProductFoundExcception;
import com.chandu.product.Exception.ProductNotFoundException;
import com.chandu.product.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;


    public Product saveProduct(Product product) {
        if(!productRepo.findByName(product.getName()).isEmpty()) {
            throw new DuplicateProductFoundExcception("Product already exists with name: " + product.getName());
        }
        return productRepo.save(product);
    }




    public Product getProductById(Long id) {
      return  productRepo.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found with id: "+id));




    }
}
