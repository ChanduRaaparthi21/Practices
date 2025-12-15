package com.chandu.s_100_exchange_set_builder.controller;

import com.chandu.s_100_exchange_set_builder.model.AuditLog;
import com.chandu.s_100_exchange_set_builder.repository.AuditLogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@Tag(name = "Audit Logs", description = "Audit log viewing APIs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    @Operation(summary = "Get all audit logs with pagination")
    public ResponseEntity<Page<AuditLog>> getAllAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<AuditLog> logs = auditLogRepository.findAll(pageRequest);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recent audit logs")
    public ResponseEntity<List<AuditLog>> getRecentAuditLogs(
            @RequestParam(defaultValue = "100") int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by("timestamp").descending());
        List<AuditLog> logs = auditLogRepository.findAll(pageRequest).getContent();
        return ResponseEntity.ok(logs);
    }
}
