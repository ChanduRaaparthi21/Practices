package com.chandu.s_100_exchange_set_builder.controller;

import com.chandu.s_100_exchange_set_builder.service.ExportImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/export-import")
@Tag(name = "Export/Import", description = "Export and import exchange set metadata")
public class ExportImportController {

    @Autowired
    private ExportImportService exportImportService;

    @GetMapping("/export/{id}")
    @Operation(summary = "Export exchange set metadata")
    public ResponseEntity<FileSystemResource> exportExchangeSet(@PathVariable Long id) {
        try {
            File exportFile = exportImportService.exportExchangeSetMetadata(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + exportFile.getName())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new FileSystemResource(exportFile));
        } catch (Exception e) {
            throw new IllegalArgumentException("Export failed: " + e.getMessage());
        }
    }

    @GetMapping("/export/all")
    @Operation(summary = "Export all exchange sets metadata")
    public ResponseEntity<FileSystemResource> exportAllExchangeSets() {
        try {
            File exportFile = exportImportService.exportAllExchangeSets();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + exportFile.getName())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new FileSystemResource(exportFile));
        } catch (Exception e) {
            throw new IllegalArgumentException("Export failed: " + e.getMessage());
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Import exchange set metadata")
    public ResponseEntity<String> importExchangeSet(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("import", ".json");
            file.transferTo(tempFile);
            exportImportService.importExchangeSetMetadata(tempFile);
            tempFile.delete();
            return ResponseEntity.ok("Import successful");
        } catch (Exception e) {
            throw new IllegalArgumentException("Import failed: " + e.getMessage());
        }
    }
}
