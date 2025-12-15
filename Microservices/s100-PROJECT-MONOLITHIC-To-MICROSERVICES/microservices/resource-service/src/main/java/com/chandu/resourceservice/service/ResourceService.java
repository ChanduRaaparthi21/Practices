package main.java.com.chandu.resourceservice.service;

import com.chandu.resourceservice.client.AuditClient;
import com.chandu.resourceservice.model.Resource;
import com.chandu.resourceservice.model.ResourceType;
import com.chandu.resourceservice.repository.ResourceRepository;
import com.chandu.resourceservice.util.CryptoUtils;
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
    private CryptoUtils cryptoUtils;

    @Autowired
    private AuditClient auditClient;

    private static final String DEFAULT_WORKSPACE_PATH = "C:/Users/chand/Desktop/s100_workspace"; // Temporary default

    public Resource addResource(MultipartFile file, ResourceType type) throws Exception {
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
        Path resourcesDir = Paths.get(DEFAULT_WORKSPACE_PATH, "resources");
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

        Resource savedResource = resourceRepository.save(resource);
        try {
            auditClient.logAction(java.util.Map.of("action", "RESOURCE_ADDED", "details",
                    String.format("Resource added: %s (Type: %s)", fileName, type)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedResource;
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
