package com.chandu.product.Controller;

import com.chandu.product.Entity.Product;
import com.chandu.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("save")
    public Product saveProduct(@RequestBody  Product product) {
        productService.saveProduct(product);
        return product;

    }


    @GetMapping("byId/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);

    }

}
