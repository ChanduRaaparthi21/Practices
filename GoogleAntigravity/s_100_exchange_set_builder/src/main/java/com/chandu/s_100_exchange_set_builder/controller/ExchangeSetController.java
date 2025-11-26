package com.chandu.s_100_exchange_set_builder.controller;

import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import com.chandu.s_100_exchange_set_builder.model.ExchangeSetDto;
import com.chandu.s_100_exchange_set_builder.service.ExchangeSetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-sets")
@Tag(name = "Exchange Sets", description = "Exchange Set management APIs")
public class ExchangeSetController {

    @Autowired
    private ExchangeSetService exchangeSetService;

    @GetMapping
    @Operation(summary = "List all exchange sets")
    public ResponseEntity<List<ExchangeSet>> getAllExchangeSets() {
        return ResponseEntity.ok(exchangeSetService.getAllExchangeSets());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exchange set details")
    public ResponseEntity<ExchangeSet> getExchangeSet(@PathVariable Long id) {
        return ResponseEntity.ok(exchangeSetService.getExchangeSet(id));
    }

    @PostMapping
    @Operation(summary = "Create an exchange set")
    public ResponseEntity<ExchangeSet> createExchangeSet(@Valid @RequestBody ExchangeSetDto dto) {
        try {
            ExchangeSet exchangeSet = exchangeSetService.createExchangeSet(dto.getName());
            return ResponseEntity.ok(exchangeSet);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exchange set")
    public ResponseEntity<Void> deleteExchangeSet(@PathVariable Long id) {
        try {
            exchangeSetService.deleteExchangeSet(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @PostMapping("/{id}/lock")
    @Operation(summary = "Lock an exchange set")
    public ResponseEntity<Void> lockExchangeSet(@PathVariable Long id) {
        exchangeSetService.lockExchangeSet(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unlock")
    @Operation(summary = "Unlock an exchange set")
    public ResponseEntity<Void> unlockExchangeSet(@PathVariable Long id) {
        exchangeSetService.unlockExchangeSet(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sign")
    @Operation(summary = "Sign an exchange set")
    public ResponseEntity<Void> signExchangeSet(@PathVariable Long id, @RequestParam Long privateKeyId) {
        try {
            exchangeSetService.signExchangeSet(id, privateKeyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @PostMapping("/{id}/package")
    @Operation(summary = "Package an exchange set")
    public ResponseEntity<FileSystemResource> packageExchangeSet(@PathVariable Long id) {
        try {
            File zipFile = exchangeSetService.packageExchangeSet(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(zipFile));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
