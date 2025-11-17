package com.chandu.orderservice.controller;


import com.chandu.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/orders/users")
    public String getUsers() {
        return service.getUsersFromUserService();
    }
}
