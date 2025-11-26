package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import com.chandu.s_100_exchange_set_builder.model.Resource;
import com.chandu.s_100_exchange_set_builder.model.Workspace;
import com.chandu.s_100_exchange_set_builder.repository.ExchangeSetRepository;
import com.chandu.s_100_exchange_set_builder.repository.ResourceRepository;
import com.chandu.s_100_exchange_set_builder.util.PackageUtils;
import com.chandu.s_100_exchange_set_builder.util.S100XmlGenerator;
import com.chandu.s_100_exchange_set_builder.util.SignatureUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExchangeSetService {

    @Autowired
    private ExchangeSetRepository exchangeSetRepository;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private S100XmlGenerator xmlGenerator;

    @Autowired
    private SignatureUtils signatureUtils;

    @Autowired
    private PackageUtils packageUtils;

    @Autowired
    private AuditLogService auditLogService;

    @Transactional
    public ExchangeSet createExchangeSet(String name) throws IOException {
        Workspace workspace = workspaceService.getCurrentWorkspace();

        if (exchangeSetRepository.existsByName(name)) {
            throw new IllegalArgumentException("Exchange Set with name " + name + " already exists.");
        }

        Path exchangeSetPath = Paths.get(workspace.getPath(), "exchange_sets", name);
        if (Files.exists(exchangeSetPath)) {
            throw new IllegalArgumentException("Directory already exists: " + exchangeSetPath);
        }

        Files.createDirectories(exchangeSetPath);

        ExchangeSet exchangeSet = new ExchangeSet();
        exchangeSet.setName(name);
        exchangeSet.setPath(exchangeSetPath.toString());

        // Generate initial CATALOG.XML
        File catalogFile = exchangeSetPath.resolve("CATALOG.XML").toFile();
        xmlGenerator.generateCatalogXml(exchangeSet, catalogFile);

        ExchangeSet savedExchangeSet = exchangeSetRepository.save(exchangeSet);
        auditLogService.logExchangeSetCreated(name);
        return savedExchangeSet;
    }

    public List<ExchangeSet> getAllExchangeSets() {
        return exchangeSetRepository.findAll();
    }

    public ExchangeSet getExchangeSet(Long id) {
        return exchangeSetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exchange Set not found"));
    }

    @Transactional
    public void deleteExchangeSet(Long id) throws IOException {
        ExchangeSet exchangeSet = getExchangeSet(id);
        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked and cannot be deleted.");
        }

        File directory = new File(exchangeSet.getPath());
        FileUtils.deleteDirectory(directory);

        exchangeSetRepository.delete(exchangeSet);
    }

    @Transactional
    public void lockExchangeSet(Long id) {
        ExchangeSet exchangeSet = getExchangeSet(id);
        exchangeSet.setLocked(true);
        exchangeSetRepository.save(exchangeSet);
    }

    @Transactional
    public void unlockExchangeSet(Long id) {
        ExchangeSet exchangeSet = getExchangeSet(id);
        exchangeSet.setLocked(false);
        exchangeSetRepository.save(exchangeSet);
    }

    public void signExchangeSet(Long id, Long privateKeyId) throws Exception {
        ExchangeSet exchangeSet = getExchangeSet(id);
        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked.");
        }

        Resource privateKey = resourceRepository.findById(privateKeyId)
                .orElseThrow(() -> new IllegalArgumentException("Private key not found"));

        File exchangeSetDir = new File(exchangeSet.getPath());
        File catalogFile = new File(exchangeSetDir, "CATALOG.XML"); // Assuming we sign catalog or whole dir?
                                                                    // Requirement says "Sign entire exchange set ->
                                                                    // Generate CATALOG.SIGN"
        // Usually CATALOG.SIGN signs CATALOG.XML or the whole content. Let's assume
        // signing CATALOG.XML for now as per standard practice or sign a digest of it.
        // Requirement: "Generate CATALOG.SIGN using private key + certificate"

        File signatureFile = new File(exchangeSetDir, "CATALOG.SIGN");
        signatureUtils.signFile(catalogFile, new File(privateKey.getFilePath()), signatureFile);

        auditLogService.logExchangeSetSigned(exchangeSet.getName());
    }

    public File packageExchangeSet(Long id) throws IOException {
        ExchangeSet exchangeSet = getExchangeSet(id);
        // Requirement: "Locked exchange set" in negative scenarios for packaging?
        // Actually US-9 says Negative: "Locked exchange set". Wait, usually you package
        // a finalized (locked) set?
        // Let's check US-11: "Locked: Cannot modify, delete, add content, package, or
        // sign".
        // So if locked, cannot package? That seems odd. Usually you lock THEN package.
        // Let's re-read US-11. "Locked: Cannot modify, delete, add content, package, or
        // sign".
        // Okay, so you must package BEFORE locking? Or maybe "package" modifies it
        // (creates zip inside)?
        // If zip is created outside, maybe it's allowed.
        // But US-9 Negative Scenarios include "Locked exchange set". So I must respect
        // that.

        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked.");
        }

        File sourceDir = new File(exchangeSet.getPath());
        File zipFile = new File(sourceDir.getParent(), exchangeSet.getName() + ".zip"); // Create zip alongside
                                                                                        // directory

        packageUtils.zipDirectory(sourceDir, zipFile);

        auditLogService.logExchangeSetPackaged(exchangeSet.getName());

        return zipFile;
    }
}
