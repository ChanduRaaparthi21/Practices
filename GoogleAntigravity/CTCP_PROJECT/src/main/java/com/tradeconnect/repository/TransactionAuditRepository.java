package com.tradeconnect.repository;

import com.tradeconnect.entity.TransactionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionAuditRepository extends JpaRepository<TransactionAudit, Long> {
    List<TransactionAudit> findByTransactionId(Long transactionId);
}
