package com.chandu.resourceservice.controller;

import com.chandu.resourceservice.model.Resource;
import com.chandu.resourceservice.model.ResourceType;
import com.chandu.resourceservice.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@Tag(name = "Resources", description = "Resource management APIs")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    @Operation(summary = "List all resources")
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a resource")
    public ResponseEntity<Resource> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") ResourceType type) {
        try {
            Resource resource = resourceService.addResource(file, type);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a resource")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
