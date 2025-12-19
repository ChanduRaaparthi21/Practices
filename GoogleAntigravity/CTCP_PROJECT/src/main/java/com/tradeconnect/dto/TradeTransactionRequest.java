package com.tradeconnect.dto;

import com.tradeconnect.entity.TransactionType;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TradeTransactionRequest {
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String remarks;
}
