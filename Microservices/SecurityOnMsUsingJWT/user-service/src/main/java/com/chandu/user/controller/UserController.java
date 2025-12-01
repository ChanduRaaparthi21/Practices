
package com.chandu.user.controller;

import com.chandu.user.client.ProductClient;
import com.chandu.user.model.ProductResponse;
import com.chandu.user.model.UserWithProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ProductClient productClient;

    public UserController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/{id}")
    public UserWithProductResponse getUserWithProduct(@PathVariable String id) {
        ProductResponse product = productClient.getProduct("101");

        UserWithProductResponse response = new UserWithProductResponse();
        response.setUserId(id);
        response.setUserName("Chandu");
        response.setProduct(product);
        return response;
    }


}
