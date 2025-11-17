package com.chandu.orderservice.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUsersFromUserService() {
        // NOTE: use service name (case-insensitive depending on setup)
        String url = "http://USER-SERVICE/users";
        return restTemplate.getForObject(url, String.class);
    }
}
