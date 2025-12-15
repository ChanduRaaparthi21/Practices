package com.chandu.resourceservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "audit-service")
public interface AuditClient {

    @PostMapping("/api/audit")
    void logAction(@RequestBody Map<String, String> request);
}
