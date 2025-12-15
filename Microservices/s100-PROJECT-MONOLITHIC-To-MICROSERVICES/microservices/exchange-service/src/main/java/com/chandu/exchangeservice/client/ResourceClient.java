package com.chandu.exchangeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resource-service")
public interface ResourceClient {

    // Define methods to interact with Resource Service if needed
    // For example, to get private key path
    @GetMapping("/api/resources/{id}/path")
    String getResourcePath(@PathVariable("id") Long id);
}
