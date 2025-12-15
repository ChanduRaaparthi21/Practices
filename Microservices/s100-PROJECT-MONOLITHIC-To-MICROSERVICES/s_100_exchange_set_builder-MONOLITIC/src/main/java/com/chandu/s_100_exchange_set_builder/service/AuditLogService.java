package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.AuditLog;
import com.chandu.s_100_exchange_set_builder.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String action, String details) {
        AuditLog auditLog = new AuditLog(action, details);
        auditLogRepository.save(auditLog);
    }

    public void logWorkspaceCreated(String path) {
        log("WORKSPACE_CREATED", "Workspace created at: " + path);
    }

    public void logResourceAdded(String fileName, String type) {
        log("RESOURCE_ADDED", String.format("Resource added: %s (Type: %s)", fileName, type));
    }

    public void logExchangeSetCreated(String name) {
        log("EXCHANGE_SET_CREATED", "Exchange Set created: " + name);
    }

    public void logExchangeSetSigned(String name) {
        log("EXCHANGE_SET_SIGNED", "Exchange Set signed: " + name);
    }

    public void logExchangeSetPackaged(String name) {
        log("EXCHANGE_SET_PACKAGED", "Exchange Set packaged: " + name);
    }

    public void logContentAdded(String fileName, String exchangeSetName) {
        log("CONTENT_ADDED", String.format("Content added to %s: %s", exchangeSetName, fileName));
    }
}
