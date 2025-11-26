package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.Workspace;
import com.chandu.s_100_exchange_set_builder.repository.WorkspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class WorkspaceService {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceService.class);
    private static final String EXCHANGE_SETS_DIR = "exchange_sets";
    private static final String RESOURCES_DIR = "resources";

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public Workspace createWorkspace(String pathStr) {
        logger.info("Attempting to create workspace at: {}", pathStr);

        Path path = Paths.get(pathStr);
        File directory = path.toFile();

        // Validation
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IllegalArgumentException("Could not create workspace directory at: " + pathStr);
            }
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory: " + pathStr);
        }

        if (!directory.canWrite()) {
            throw new IllegalArgumentException("No write permission for directory: " + pathStr);
        }

        // Create subdirectories
        createSubdirectory(path, EXCHANGE_SETS_DIR);
        createSubdirectory(path, RESOURCES_DIR);

        // Save to DB
        // Check if workspace already exists with this path
        // For simplicity, we might just have one active workspace or allow switching.
        // Requirement says "Selecting workspace directory", "Persist directory".
        // We'll save it.

        Workspace workspace = new Workspace();
        workspace.setPath(pathStr);
        return workspaceRepository.save(workspace);
    }

    public Workspace getCurrentWorkspace() {
        return workspaceRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new IllegalStateException("No workspace configured"));
    }

    public boolean isWorkspaceConfigured() {
        return workspaceRepository.findTopByOrderByIdDesc().isPresent();
    }

    private void createSubdirectory(Path parent, String subDirName) {
        Path subPath = parent.resolve(subDirName);
        File subDir = subPath.toFile();
        if (!subDir.exists()) {
            if (!subDir.mkdirs()) {
                throw new RuntimeException("Failed to create subdirectory: " + subDirName);
            }
        }
    }
}
