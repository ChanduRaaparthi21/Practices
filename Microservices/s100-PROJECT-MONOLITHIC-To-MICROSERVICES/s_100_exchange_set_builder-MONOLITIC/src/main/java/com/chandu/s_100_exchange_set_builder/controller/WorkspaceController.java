package com.chandu.s_100_exchange_set_builder.controller;

import com.chandu.s_100_exchange_set_builder.model.Workspace;
import com.chandu.s_100_exchange_set_builder.model.WorkspaceDto;
import com.chandu.s_100_exchange_set_builder.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspace")
@Tag(name = "Workspace", description = "Workspace management APIs")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping
    @Operation(summary = "Set workspace directory")
    public ResponseEntity<Workspace> setWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto) {
        Workspace workspace = workspaceService.createWorkspace(workspaceDto.getPath());
        return ResponseEntity.ok(workspace);
    }

    @GetMapping
    @Operation(summary = "Get current workspace")
    public ResponseEntity<Workspace> getWorkspace() {
        try {
            Workspace workspace = workspaceService.getCurrentWorkspace();
            return ResponseEntity.ok(workspace);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
