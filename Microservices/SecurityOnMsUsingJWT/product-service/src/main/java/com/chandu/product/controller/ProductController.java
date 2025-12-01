
package com.chandu.product.controller;

import com.chandu.product.model.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable String id) {
        ProductResponse product = new ProductResponse();
        product.setId(id);
        product.setName("Laptop");
        product.setPrice(55000.0);
        return product;
    }
}
