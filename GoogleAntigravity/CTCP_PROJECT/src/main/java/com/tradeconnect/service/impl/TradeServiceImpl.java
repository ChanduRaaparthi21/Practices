package com.tradeconnect.service.impl;

import com.tradeconnect.dto.DashboardStats;
import com.tradeconnect.dto.TradeTransactionRequest;
import com.tradeconnect.entity.*;
import com.tradeconnect.repository.TradeTransactionRepository;
import com.tradeconnect.repository.TransactionAuditRepository;
import com.tradeconnect.service.TradeService;
import com.tradeconnect.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeTransactionRepository transactionRepository;
    private final TransactionAuditRepository auditRepository;

    @Override
    @Transactional
    @PreAuthorize("hasRole('CORPORATE_MAKER')")
    public TradeTransaction initiateTransaction(TradeTransactionRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        TradeTransaction transaction = TradeTransaction.builder()
                .referenceNumber("TF" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .type(request.getType())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(TransactionStatus.SUBMITTED)
                .organization(currentUser.getOrganization())
                .maker(currentUser)
                .build();

        transaction = transactionRepository.save(transaction);
        saveAudit(transaction, "SUBMITTED", "Transaction initiated", currentUser.getUsername());
        return transaction;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('CORPORATE_CHECKER')")
    public TradeTransaction checkTransaction(Long id, String remarks) {
        TradeTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setStatus(TransactionStatus.PENDING_APPROVAL);
        transaction.setChecker(SecurityUtils.getCurrentUser());
        transaction.setRemarks(remarks);

        saveAudit(transaction, "CHECKED", remarks, SecurityUtils.getCurrentUser().getUsername());
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('CORPORATE_AUTHORIZER')")
    public TradeTransaction authorizeTransaction(Long id, String remarks) {
        TradeTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setStatus(TransactionStatus.APPROVED);
        transaction.setAuthorizer(SecurityUtils.getCurrentUser());

        saveAudit(transaction, "AUTHORIZED", remarks, SecurityUtils.getCurrentUser().getUsername());
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('CORPORATE_CHECKER', 'CORPORATE_AUTHORIZER')")
    public TradeTransaction rejectTransaction(Long id, String remarks) {
        TradeTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setStatus(TransactionStatus.REJECTED);
        saveAudit(transaction, "REJECTED", remarks, SecurityUtils.getCurrentUser().getUsername());
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TradeTransaction> getPendingActions() {
        // Logic depends on the role
        User user = SecurityUtils.getCurrentUser();
        if (user.getRoles().contains(Role.ROLE_CORPORATE_MAKER)) {
            return transactionRepository.findByMakerId(user.getId());
        } else if (user.getRoles().contains(Role.ROLE_CORPORATE_CHECKER)) {
            return transactionRepository.findByStatusAndOrganizationId(TransactionStatus.SUBMITTED,
                    user.getOrganization().getId());
        } else if (user.getRoles().contains(Role.ROLE_CORPORATE_AUTHORIZER)) {
            return transactionRepository.findByStatusAndOrganizationId(TransactionStatus.PENDING_APPROVAL,
                    user.getOrganization().getId());
        } else if (user.getRoles().contains(Role.ROLE_BANK_ADMIN)) {
            return transactionRepository.findAll();
        }
        return List.of();
    }

    @Override
    public TradeTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }

    @Override
    public DashboardStats getDashboardStats() {
        User user = SecurityUtils.getCurrentUser();
        Long orgId = user.getOrganization() != null ? user.getOrganization().getId() : null;

        List<TradeTransaction> relevantTrades;
        if (user.getRoles().contains(Role.ROLE_CORPORATE_MAKER)) {
            relevantTrades = transactionRepository.findByMakerId(user.getId());
        } else if (user.getRoles().contains(Role.ROLE_BANK_ADMIN)) {
            relevantTrades = transactionRepository.findAll();
        } else if (orgId != null) {
            relevantTrades = transactionRepository.findByOrganizationId(orgId);
        } else {
            relevantTrades = transactionRepository.findAll();
        }

        long totalActive = relevantTrades.stream()
                .filter(t -> t.getStatus() != TransactionStatus.APPROVED && t.getStatus() != TransactionStatus.REJECTED)
                .count();

        long pendingMyAction = getPendingActions().size();

        long approvedToday = relevantTrades.stream()
                .filter(t -> t.getStatus() == TransactionStatus.APPROVED)
                .filter(t -> t.getUpdatedAt() != null
                        && t.getUpdatedAt().toLocalDate().equals(LocalDateTime.now().toLocalDate()))
                .count();

        return DashboardStats.builder()
                .totalActiveTrades(totalActive)
                .pendingMyAction(pendingMyAction)
                .approvedToday(approvedToday)
                .build();
    }

    private void saveAudit(TradeTransaction transaction, String action, String remarks, String user) {
        TransactionAudit audit = TransactionAudit.builder()
                .transaction(transaction)
                .action(action)
                .remarks(remarks)
                .performedBy(user)
                .timestamp(LocalDateTime.now())
                .build();
        auditRepository.save(audit);
    }
}
