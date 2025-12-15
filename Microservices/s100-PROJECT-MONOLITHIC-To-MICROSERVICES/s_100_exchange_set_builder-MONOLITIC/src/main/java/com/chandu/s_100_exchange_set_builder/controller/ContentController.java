package com.chandu.s_100_exchange_set_builder.controller;

import com.chandu.s_100_exchange_set_builder.model.ContentItem;
import com.chandu.s_100_exchange_set_builder.model.ContentType;
import com.chandu.s_100_exchange_set_builder.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@Tag(name = "Content", description = "Content management APIs")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/{exchangeSetId}")
    @Operation(summary = "List content items for an exchange set")
    public ResponseEntity<List<ContentItem>> getContentItems(@PathVariable Long exchangeSetId) {
        return ResponseEntity.ok(contentService.getContentItems(exchangeSetId));
    }

    @PostMapping(value = "/{exchangeSetId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add content to exchange set")
    public ResponseEntity<ContentItem> addContent(
            @PathVariable Long exchangeSetId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") ContentType type) {
        try {
            ContentItem contentItem = contentService.addContent(exchangeSetId, file, type);
            return ResponseEntity.ok(contentItem);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove content item")
    public ResponseEntity<Void> removeContent(@PathVariable Long id) {
        try {
            contentService.removeContent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @PostMapping("/{id}/sign")
    @Operation(summary = "Sign content item")
    public ResponseEntity<Void> signContent(@PathVariable Long id, @RequestParam Long privateKeyId) {
        try {
            contentService.signContent(id, privateKeyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
