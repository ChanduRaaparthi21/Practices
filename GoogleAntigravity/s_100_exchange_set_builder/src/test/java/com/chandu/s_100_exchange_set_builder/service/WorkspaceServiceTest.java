package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.Workspace;
import com.chandu.s_100_exchange_set_builder.repository.WorkspaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @InjectMocks
    private WorkspaceService workspaceService;

    @Test
    void testGetCurrentWorkspace_Success() {
        Workspace workspace = new Workspace();
        workspace.setPath("C:\\test\\workspace");

        when(workspaceRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(workspace));

        Workspace result = workspaceService.getCurrentWorkspace();

        assertNotNull(result);
        assertEquals("C:\\test\\workspace", result.getPath());
        verify(workspaceRepository, times(1)).findTopByOrderByIdDesc();
    }

    @Test
    void testGetCurrentWorkspace_NotFound() {
        when(workspaceRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            workspaceService.getCurrentWorkspace();
        });
    }

    @Test
    void testIsWorkspaceConfigured_True() {
        Workspace workspace = new Workspace();
        when(workspaceRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(workspace));

        boolean result = workspaceService.isWorkspaceConfigured();

        assertTrue(result);
    }

    @Test
    void testIsWorkspaceConfigured_False() {
        when(workspaceRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());

        boolean result = workspaceService.isWorkspaceConfigured();

        assertFalse(result);
    }
}
