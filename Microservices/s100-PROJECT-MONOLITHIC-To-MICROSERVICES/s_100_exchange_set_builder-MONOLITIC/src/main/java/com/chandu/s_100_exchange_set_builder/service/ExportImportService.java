package com.chandu.s_100_exchange_set_builder.service;

import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import com.chandu.s_100_exchange_set_builder.repository.ExchangeSetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ExportImportService {

    @Autowired
    private ExchangeSetRepository exchangeSetRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Export exchange set metadata to JSON
     */
    public File exportExchangeSetMetadata(Long id) throws IOException {
        ExchangeSet exchangeSet = exchangeSetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exchange Set not found"));

        File exportFile = new File(System.getProperty("java.io.tmpdir"),
                exchangeSet.getName() + "_metadata.json");

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(exportFile, exchangeSet);
        return exportFile;
    }

    /**
     * Export all exchange sets metadata
     */
    public File exportAllExchangeSets() throws IOException {
        List<ExchangeSet> exchangeSets = exchangeSetRepository.findAll();

        File exportFile = new File(System.getProperty("java.io.tmpdir"),
                "all_exchange_sets_" + System.currentTimeMillis() + ".json");

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(exportFile, exchangeSets);
        return exportFile;
    }

    /**
     * Import exchange set metadata from JSON
     * Note: This would need additional logic to recreate the actual files
     */
    public ExchangeSet importExchangeSetMetadata(File jsonFile) throws IOException {
        ExchangeSet exchangeSet = objectMapper.readValue(jsonFile, ExchangeSet.class);
        // In production, you would also need to:
        // 1. Validate the data
        // 2. Check for conflicts
        // 3. Recreate the directory structure
        // 4. Import content files
        return exchangeSetRepository.save(exchangeSet);
    }
}
