package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import com.chandu.s_100_exchange_set_builder.model.Resource;
import com.chandu.s_100_exchange_set_builder.repository.ExchangeSetRepository;
import com.chandu.s_100_exchange_set_builder.repository.ResourceRepository;
import com.chandu.s_100_exchange_set_builder.util.PackageUtils;
import com.chandu.s_100_exchange_set_builder.util.S100XmlGenerator;
import com.chandu.s_100_exchange_set_builder.util.SignatureUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeSetServiceTest {

    @Mock
    private ExchangeSetRepository exchangeSetRepository;

    @Mock
    private WorkspaceService workspaceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private S100XmlGenerator xmlGenerator;

    @Mock
    private SignatureUtils signatureUtils;

    @Mock
    private PackageUtils packageUtils;

    @InjectMocks
    private ExchangeSetService exchangeSetService;

    @TempDir
    Path tempDir;

    @Test
    void testCreateExchangeSet_Success() throws Exception {
        String name = "TestExchangeSet";
        com.chandu.s_100_exchange_set_builder.model.Workspace workspace = new com.chandu.s_100_exchange_set_builder.model.Workspace();
        workspace.setPath(tempDir.toString());

        when(workspaceService.getCurrentWorkspace()).thenReturn(workspace);
        when(exchangeSetRepository.existsByName(name)).thenReturn(false);
        when(exchangeSetRepository.save(any(ExchangeSet.class))).thenAnswer(i -> i.getArgument(0));

        ExchangeSet result = exchangeSetService.createExchangeSet(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(xmlGenerator, times(1)).generateCatalogXml(any(), any());
        verify(exchangeSetRepository, times(1)).save(any(ExchangeSet.class));
    }

    @Test
    void testCreateExchangeSet_DuplicateName() {
        String name = "DuplicateSet";
        com.chandu.s_100_exchange_set_builder.model.Workspace workspace = new com.chandu.s_100_exchange_set_builder.model.Workspace();
        workspace.setPath(tempDir.toString());

        when(workspaceService.getCurrentWorkspace()).thenReturn(workspace);
        when(exchangeSetRepository.existsByName(name)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            exchangeSetService.createExchangeSet(name);
        });

        verify(exchangeSetRepository, never()).save(any());
    }

    @Test
    void testLockExchangeSet_Success() {
        Long id = 1L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setId(id);
        exchangeSet.setLocked(false);

        when(exchangeSetRepository.findById(id)).thenReturn(Optional.of(exchangeSet));
        when(exchangeSetRepository.save(any(ExchangeSet.class))).thenAnswer(i -> i.getArgument(0));

        exchangeSetService.lockExchangeSet(id);

        assertTrue(exchangeSet.isLocked());
        verify(exchangeSetRepository, times(1)).save(exchangeSet);
    }

    @Test
    void testUnlockExchangeSet_Success() {
        Long id = 1L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setId(id);
        exchangeSet.setLocked(true);

        when(exchangeSetRepository.findById(id)).thenReturn(Optional.of(exchangeSet));
        when(exchangeSetRepository.save(any(ExchangeSet.class))).thenAnswer(i -> i.getArgument(0));

        exchangeSetService.unlockExchangeSet(id);

        assertFalse(exchangeSet.isLocked());
        verify(exchangeSetRepository, times(1)).save(exchangeSet);
    }

    @Test
    void testDeleteExchangeSet_Locked() {
        Long id = 1L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setId(id);
        exchangeSet.setLocked(true);
        exchangeSet.setPath(tempDir.toString());

        when(exchangeSetRepository.findById(id)).thenReturn(Optional.of(exchangeSet));

        assertThrows(IllegalStateException.class, () -> {
            exchangeSetService.deleteExchangeSet(id);
        });

        verify(exchangeSetRepository, never()).delete(any());
    }

    @Test
    void testSignExchangeSet_Locked() throws Exception {
        Long id = 1L;
        Long privateKeyId = 2L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setId(id);
        exchangeSet.setLocked(true);

        when(exchangeSetRepository.findById(id)).thenReturn(Optional.of(exchangeSet));

        assertThrows(IllegalStateException.class, () -> {
            exchangeSetService.signExchangeSet(id, privateKeyId);
        });

        verify(signatureUtils, never()).signFile(any(), any(), any());
    }
}
