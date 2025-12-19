package com.tradeconnect.service;

import com.tradeconnect.dto.DashboardStats;
import com.tradeconnect.dto.TradeTransactionRequest;
import com.tradeconnect.entity.TradeTransaction;
import java.util.List;

public interface TradeService {
    TradeTransaction initiateTransaction(TradeTransactionRequest request);

    TradeTransaction checkTransaction(Long id, String remarks);

    TradeTransaction authorizeTransaction(Long id, String remarks);

    TradeTransaction rejectTransaction(Long id, String remarks);

    List<TradeTransaction> getPendingActions();

    TradeTransaction getTransactionById(Long id);

    DashboardStats getDashboardStats();
}
