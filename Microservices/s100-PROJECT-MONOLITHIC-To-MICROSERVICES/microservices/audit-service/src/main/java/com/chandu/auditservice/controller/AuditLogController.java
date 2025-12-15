package com.chandu.auditservice.controller;

import com.chandu.auditservice.model.AuditLog;
import com.chandu.auditservice.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/audit")
@Tag(name = "Audit Logs", description = "Audit log viewing APIs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    @Operation(summary = "Log an action")
    public ResponseEntity<Void> logAction(@RequestBody Map<String, String> request) {
        String action = request.get("action");
        String details = request.get("details");
        auditLogService.log(action, details);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all audit logs")
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }
}
