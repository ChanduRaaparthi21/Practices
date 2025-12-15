package com.chandu.exchangeservice.controller;

import com.chandu.exchangeservice.model.ExchangeSet;
import com.chandu.exchangeservice.model.ExchangeSetDto;
import com.chandu.exchangeservice.service.ExchangeSetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-sets")
public class ExchangeSetController {

    @Autowired
    private ExchangeSetService exchangeSetService;

    @PostMapping
    public ResponseEntity<ExchangeSet> createExchangeSet(@Valid @RequestBody ExchangeSetDto dto) throws IOException {
        ExchangeSet created = exchangeSetService.createExchangeSet(dto.getName());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ExchangeSet>> getAllExchangeSets() {
        return ResponseEntity.ok(exchangeSetService.getAllExchangeSets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeSet> getExchangeSet(@PathVariable Long id) {
        return ResponseEntity.ok(exchangeSetService.getExchangeSet(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchangeSet(@PathVariable Long id) throws IOException {
        exchangeSetService.deleteExchangeSet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/lock")
    public ResponseEntity<Void> lockExchangeSet(@PathVariable Long id) {
        exchangeSetService.lockExchangeSet(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unlock")
    public ResponseEntity<Void> unlockExchangeSet(@PathVariable Long id) {
        exchangeSetService.unlockExchangeSet(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sign")
    public ResponseEntity<Void> signExchangeSet(@PathVariable Long id, @RequestParam Long privateKeyId)
            throws Exception {
        exchangeSetService.signExchangeSet(id, privateKeyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/package")
    public ResponseEntity<Resource> packageExchangeSet(@PathVariable Long id) throws IOException {
        File zipFile = exchangeSetService.packageExchangeSet(id);
        Resource resource = new FileSystemResource(zipFile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFile.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
