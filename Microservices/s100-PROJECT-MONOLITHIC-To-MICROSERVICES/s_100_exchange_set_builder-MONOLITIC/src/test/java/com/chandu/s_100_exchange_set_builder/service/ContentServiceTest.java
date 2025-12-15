package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.ContentItem;
import com.chandu.s_100_exchange_set_builder.model.ContentType;
import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import com.chandu.s_100_exchange_set_builder.repository.ContentItemRepository;
import com.chandu.s_100_exchange_set_builder.repository.ExchangeSetRepository;
import com.chandu.s_100_exchange_set_builder.repository.ResourceRepository;
import com.chandu.s_100_exchange_set_builder.util.S100XmlGenerator;
import com.chandu.s_100_exchange_set_builder.util.SignatureUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentItemRepository contentItemRepository;

    @Mock
    private ExchangeSetRepository exchangeSetRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private S100XmlGenerator xmlGenerator;

    @Mock
    private SignatureUtils signatureUtils;

    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private ContentService contentService;

    @TempDir
    Path tempDir;

    @Test
    void testAddContent_LockedExchangeSet() {
        Long exchangeSetId = 1L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setId(exchangeSetId);
        exchangeSet.setLocked(true);

        when(exchangeSetRepository.findById(exchangeSetId)).thenReturn(Optional.of(exchangeSet));

        assertThrows(IllegalStateException.class, () -> {
            contentService.addContent(exchangeSetId, mockFile, ContentType.DATASET);
        });

        verify(contentItemRepository, never()).save(any());
    }

    @Test
    void testRemoveContent_LockedExchangeSet() {
        Long contentId = 1L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setLocked(true);

        ContentItem contentItem = new ContentItem();
        contentItem.setId(contentId);
        contentItem.setExchangeSet(exchangeSet);

        when(contentItemRepository.findById(contentId)).thenReturn(Optional.of(contentItem));

        assertThrows(IllegalStateException.class, () -> {
            contentService.removeContent(contentId);
        });

        verify(contentItemRepository, never()).delete(any());
    }

    @Test
    void testSignContent_AlreadySigned() throws Exception {
        Long contentId = 1L;
        Long privateKeyId = 2L;
        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setLocked(false);

        ContentItem contentItem = new ContentItem();
        contentItem.setId(contentId);
        contentItem.setExchangeSet(exchangeSet);
        contentItem.setSigned(true);

        when(contentItemRepository.findById(contentId)).thenReturn(Optional.of(contentItem));

        assertThrows(IllegalArgumentException.class, () -> {
            contentService.signContent(contentId, privateKeyId);
        });

        verify(signatureUtils, never()).signFile(any(), any(), any());
    }
}
