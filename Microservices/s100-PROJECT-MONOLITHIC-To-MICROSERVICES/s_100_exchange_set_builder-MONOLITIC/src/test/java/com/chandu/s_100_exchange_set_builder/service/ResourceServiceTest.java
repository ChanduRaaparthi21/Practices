package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.Resource;
import com.chandu.s_100_exchange_set_builder.model.ResourceType;
import com.chandu.s_100_exchange_set_builder.model.Workspace;
import com.chandu.s_100_exchange_set_builder.repository.ResourceRepository;
import com.chandu.s_100_exchange_set_builder.util.CryptoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private WorkspaceService workspaceService;

    @Mock
    private CryptoUtils cryptoUtils;

    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private ResourceService resourceService;

    @TempDir
    Path tempDir;

    @Test
    void testAddResource_DuplicateFileName() throws Exception {
        String fileName = "test.key";
        Workspace workspace = new Workspace();
        workspace.setPath(tempDir.toString());

        when(mockFile.getOriginalFilename()).thenReturn(fileName);
        when(workspaceService.getCurrentWorkspace()).thenReturn(workspace);
        when(resourceRepository.findByFileName(fileName)).thenReturn(Optional.of(new Resource()));

        assertThrows(IllegalArgumentException.class, () -> {
            resourceService.addResource(mockFile, ResourceType.PRIVATE_KEY);
        });

        verify(resourceRepository, never()).save(any());
    }

    @Test
    void testAddResource_InvalidCertificate() throws Exception {
        String fileName = "test.crt";
        Workspace workspace = new Workspace();
        workspace.setPath(tempDir.toString());

        when(mockFile.getOriginalFilename()).thenReturn(fileName);
        when(mockFile.getBytes()).thenReturn(new byte[] { 1, 2, 3 });
        when(workspaceService.getCurrentWorkspace()).thenReturn(workspace);
        when(resourceRepository.findByFileName(fileName)).thenReturn(Optional.empty());
        doThrow(new Exception("Invalid certificate")).when(cryptoUtils).validateCertificate(any());

        assertThrows(IllegalArgumentException.class, () -> {
            resourceService.addResource(mockFile, ResourceType.CERTIFICATE);
        });

        verify(resourceRepository, never()).save(any());
    }
}
