package com.chandu.auditservice.service;

import com.chandu.auditservice.model.AuditLog;
import com.chandu.auditservice.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String action, String details) {
        AuditLog auditLog = new AuditLog(action, details);
        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
