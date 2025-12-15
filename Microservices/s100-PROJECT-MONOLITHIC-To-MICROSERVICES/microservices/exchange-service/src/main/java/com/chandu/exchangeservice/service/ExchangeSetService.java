package main.java.com.chandu.exchangeservice.service;

import com.chandu.exchangeservice.client.AuditClient;
import com.chandu.exchangeservice.client.ResourceClient;
import com.chandu.exchangeservice.model.ExchangeSet;
import com.chandu.exchangeservice.repository.ExchangeSetRepository;
import com.chandu.exchangeservice.util.PackageUtils;
import com.chandu.exchangeservice.util.S100XmlGenerator;
import com.chandu.exchangeservice.util.SignatureUtils;
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
    private S100XmlGenerator xmlGenerator;

    @Autowired
    private SignatureUtils signatureUtils;

    @Autowired
    private PackageUtils packageUtils;

    @Autowired
    private AuditClient auditClient;

    @Autowired
    private ResourceClient resourceClient;

    private static final String DEFAULT_WORKSPACE_PATH = "C:/Users/chand/Desktop/s100_workspace"; // Temporary default

    @Transactional
    public ExchangeSet createExchangeSet(String name) throws IOException {
        if (exchangeSetRepository.existsByName(name)) {
            throw new IllegalArgumentException("Exchange Set with name " + name + " already exists.");
        }

        Path exchangeSetPath = Paths.get(DEFAULT_WORKSPACE_PATH, "exchange_sets", name);
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
        try {
            auditClient.logAction(
                    java.util.Map.of("action", "EXCHANGE_SET_CREATED", "details", "Exchange Set created: " + name));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        // Use Resource Client to get private key path
        // String privateKeyPath = resourceClient.getResourcePath(privateKeyId);
        // File privateKeyFile = new File(privateKeyPath);
        File privateKeyFile = new File("path/to/private/key"); // Placeholder until Resource Service endpoint is ready

        File exchangeSetDir = new File(exchangeSet.getPath());
        File catalogFile = new File(exchangeSetDir, "CATALOG.XML");

        File signatureFile = new File(exchangeSetDir, "CATALOG.SIGN");
        signatureUtils.signFile(catalogFile, privateKeyFile, signatureFile);

        try {
            auditClient.logAction(java.util.Map.of("action", "EXCHANGE_SET_SIGNED", "details",
                    "Exchange Set signed: " + exchangeSet.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File packageExchangeSet(Long id) throws IOException {
        ExchangeSet exchangeSet = getExchangeSet(id);

        if (exchangeSet.isLocked()) {
            throw new IllegalStateException("Exchange Set is locked.");
        }

        File sourceDir = new File(exchangeSet.getPath());
        File zipFile = new File(sourceDir.getParent(), exchangeSet.getName() + ".zip");

        packageUtils.zipDirectory(sourceDir, zipFile);

        try {
            auditClient.logAction(java.util.Map.of("action", "EXCHANGE_SET_PACKAGED", "details",
                    "Exchange Set packaged: " + exchangeSet.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zipFile;
    }
}
