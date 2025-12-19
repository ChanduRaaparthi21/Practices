package com.tradeconnect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_audits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private TradeTransaction transaction;

    private String action; // SUBMITTED, CHECKED, AUTHORIZED, REJECTED

    private String performedBy;

    private LocalDateTime timestamp;

    private String remarks;
}
