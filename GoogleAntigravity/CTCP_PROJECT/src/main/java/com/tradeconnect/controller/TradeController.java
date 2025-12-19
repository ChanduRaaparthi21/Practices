package com.tradeconnect.controller;

import com.tradeconnect.dto.TradeTransactionRequest;
import com.tradeconnect.entity.TradeTransaction;
import com.tradeconnect.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
@Tag(name = "Trade Transactions", description = "Endpoints for managing Trade Finance workflows")
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/initiate")
    @Operation(summary = "Initiate a new trade transaction (Maker only)")
    public ResponseEntity<TradeTransaction> initiate(@RequestBody TradeTransactionRequest request) {
        return ResponseEntity.ok(tradeService.initiateTransaction(request));
    }

    @PostMapping("/{id}/check")
    @Operation(summary = "Check/Verify a transaction (Checker only)")
    public ResponseEntity<TradeTransaction> check(@PathVariable Long id, @RequestParam String remarks) {
        return ResponseEntity.ok(tradeService.checkTransaction(id, remarks));
    }

    @PostMapping("/{id}/authorize")
    @Operation(summary = "Authorize/Approve a transaction (Authorizer only)")
    public ResponseEntity<TradeTransaction> authorize(@PathVariable Long id, @RequestParam String remarks) {
        return ResponseEntity.ok(tradeService.authorizeTransaction(id, remarks));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get transactions pending action based on user role")
    public ResponseEntity<List<TradeTransaction>> getPending() {
        return ResponseEntity.ok(tradeService.getPendingActions());
    }

    @GetMapping("/dashboard-stats")
    @Operation(summary = "Get counts for dashboard stat cards")
    public ResponseEntity<com.tradeconnect.dto.DashboardStats> getStats() {
        return ResponseEntity.ok(tradeService.getDashboardStats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeTransaction> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tradeService.getTransactionById(id));
    }
}
