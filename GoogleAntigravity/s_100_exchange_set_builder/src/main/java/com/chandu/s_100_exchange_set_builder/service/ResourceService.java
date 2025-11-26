package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.Resource;
import com.chandu.s_100_exchange_set_builder.model.ResourceType;
import com.chandu.s_100_exchange_set_builder.model.Workspace;
import com.chandu.s_100_exchange_set_builder.repository.ResourceRepository;
import com.chandu.s_100_exchange_set_builder.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private CryptoUtils cryptoUtils;

    public Resource addResource(MultipartFile file, ResourceType type) throws Exception {
        Workspace workspace = workspaceService.getCurrentWorkspace();
        String fileName = file.getOriginalFilename();

        if (resourceRepository.findByFileName(fileName).isPresent()) {
            throw new IllegalArgumentException("Resource with name " + fileName + " already exists.");
        }

        // Validate content based on type
        if (type == ResourceType.CERTIFICATE) {
            try {
                cryptoUtils.validateCertificate(file.getBytes());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid or expired certificate: " + e.getMessage());
            }
        }

        // Save file
        Path resourcesDir = Paths.get(workspace.getPath(), "resources");
        if (!Files.exists(resourcesDir)) {
            Files.createDirectories(resourcesDir);
        }

        Path targetPath = resourcesDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Save metadata
        Resource resource = new Resource();
        resource.setFileName(fileName);
        resource.setFileType(type);
        resource.setFilePath(targetPath.toString());

        return resourceRepository.save(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public void deleteResource(Long id) throws IOException {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));

        Path path = Paths.get(resource.getFilePath());
        Files.deleteIfExists(path);

        resourceRepository.delete(resource);
    }
}
