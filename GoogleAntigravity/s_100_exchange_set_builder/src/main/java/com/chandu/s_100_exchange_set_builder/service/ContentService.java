package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.*;
import com.chandu.s_100_exchange_set_builder.repository.ContentItemRepository;
import com.chandu.s_100_exchange_set_builder.repository.ExchangeSetRepository;
import com.chandu.s_100_exchange_set_builder.repository.ResourceRepository;
import com.chandu.s_100_exchange_set_builder.util.S100XmlGenerator;
import com.chandu.s_100_exchange_set_builder.util.SignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentItemRepository contentItemRepository;

    @Autowired
    private ExchangeSetRepository exchangeSetRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private S100XmlGenerator xmlGenerator;

    @Autowired
    private SignatureUtils signatureUtils;

    @Autowired
    private AuditLogService auditLogService;

    @Transactional
    public ContentItem addContent(Long exchangeSetId, MultipartFile file, ContentType contentType) throws IOException {
        ExchangeSet exchangeSet = exchangeSetRepository.findById(exchangeSetId)
                .orElseThrow(() -> new IllegalArgumentException("Exchange Set not found"));

        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked.");
        }

        String fileName = file.getOriginalFilename();

        // Check for duplicates in this exchange set
        boolean exists = exchangeSet.getContentItems().stream()
                .anyMatch(item -> item.getFileName().equals(fileName));
        if (exists) {
            throw new IllegalArgumentException("File with name " + fileName + " already exists in this Exchange Set.");
        }

        Path exchangeSetPath = Paths.get(exchangeSet.getPath());
        Path targetPath = exchangeSetPath.resolve(fileName); // Flat structure for now

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        ContentItem contentItem = new ContentItem();
        contentItem.setExchangeSet(exchangeSet);
        contentItem.setFileName(fileName);
        contentItem.setFilePath(targetPath.toString());
        contentItem.setContentType(contentType);

        contentItem = contentItemRepository.save(contentItem);

        // Update CATALOG.XML
        exchangeSet.getContentItems().add(contentItem); // Add to list for XML generation
        updateCatalogXml(exchangeSet);

        auditLogService.logContentAdded(fileName, exchangeSet.getName());

        return contentItem;
    }

    @Transactional
    public void removeContent(Long contentId) throws IOException {
        ContentItem contentItem = contentItemRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content Item not found"));

        ExchangeSet exchangeSet = contentItem.getExchangeSet();
        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked.");
        }

        Path path = Paths.get(contentItem.getFilePath());
        Files.deleteIfExists(path);

        // Also delete .sign file if exists
        Path signPath = Paths.get(contentItem.getFilePath() + ".sign");
        Files.deleteIfExists(signPath);

        contentItemRepository.delete(contentItem);

        // Update CATALOG.XML
        exchangeSet.getContentItems().remove(contentItem);
        updateCatalogXml(exchangeSet);
    }

    @Transactional
    public void signContent(Long contentId, Long privateKeyId) throws Exception {
        ContentItem contentItem = contentItemRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content Item not found"));

        ExchangeSet exchangeSet = contentItem.getExchangeSet();
        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked.");
        }

        if (contentItem.isSigned()) {
            throw new IllegalArgumentException("Content is already signed.");
        }

        Resource privateKey = resourceRepository.findById(privateKeyId)
                .orElseThrow(() -> new IllegalArgumentException("Private key not found"));

        File fileToSign = new File(contentItem.getFilePath());
        File signatureFile = new File(contentItem.getFilePath() + ".sign");

        signatureUtils.signFile(fileToSign, new File(privateKey.getFilePath()), signatureFile);

        contentItem.setSigned(true);
        contentItemRepository.save(contentItem);
    }

    public List<ContentItem> getContentItems(Long exchangeSetId) {
        ExchangeSet exchangeSet = exchangeSetRepository.findById(exchangeSetId)
                .orElseThrow(() -> new IllegalArgumentException("Exchange Set not found"));
        return exchangeSet.getContentItems();
    }

    private void updateCatalogXml(ExchangeSet exchangeSet) throws IOException {
        File catalogFile = Paths.get(exchangeSet.getPath(), "CATALOG.XML").toFile();
        xmlGenerator.generateCatalogXml(exchangeSet, catalogFile);
    }
}
