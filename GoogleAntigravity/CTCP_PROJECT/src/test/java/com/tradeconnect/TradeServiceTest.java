package com.tradeconnect;

import com.tradeconnect.dto.TradeTransactionRequest;
import com.tradeconnect.entity.Role;
import com.tradeconnect.entity.TradeTransaction;
import com.tradeconnect.entity.TransactionType;
import com.tradeconnect.entity.User;
import com.tradeconnect.repository.TradeTransactionRepository;
import com.tradeconnect.repository.TransactionAuditRepository;
import com.tradeconnect.service.impl.TradeServiceImpl;
import com.tradeconnect.util.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeTransactionRepository transactionRepository;

    @Mock
    private TransactionAuditRepository auditRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    void testInitiateTransaction() {
        // Mock static SecurityUtils
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            User mockUser = User.builder()
                    .username("maker")
                    .roles(Set.of(Role.ROLE_CORPORATE_MAKER))
                    .build();
            mockedSecurity.when(SecurityUtils::getCurrentUser).thenReturn(mockUser);

            TradeTransactionRequest request = new TradeTransactionRequest();
            request.setType(TransactionType.LETTER_OF_CREDIT);
            request.setAmount(new BigDecimal("10000"));
            request.setCurrency("USD");

            when(transactionRepository.save(any(TradeTransaction.class))).thenAnswer(i -> i.getArguments()[0]);

            TradeTransaction result = tradeService.initiateTransaction(request);

            assertNotNull(result);
            assertEquals("LETTER_OF_CREDIT", result.getType().name());
            assertEquals(new BigDecimal("10000"), result.getAmount());
            verify(transactionRepository, times(1)).save(any());
        }
    }
}
