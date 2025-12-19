package com.tradeconnect.repository;

import com.tradeconnect.entity.TradeTransaction;
import com.tradeconnect.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
    List<TradeTransaction> findByStatus(TransactionStatus status);

    List<TradeTransaction> findByOrganizationId(Long organizationId);

    List<TradeTransaction> findByMakerId(Long makerId);

    List<TradeTransaction> findByStatusAndOrganizationId(TransactionStatus status, Long organizationId);
}
