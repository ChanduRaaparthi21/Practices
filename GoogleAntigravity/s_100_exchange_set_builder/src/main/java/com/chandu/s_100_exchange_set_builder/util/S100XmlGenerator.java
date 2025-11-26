package com.chandu.s_100_exchange_set_builder.util;

import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class S100XmlGenerator {

    public void generateCatalogXml(ExchangeSet exchangeSet, File outputFile) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        // Simplified S-100 Catalog Structure
        Map<String, Object> catalog = new HashMap<>();
        catalog.put("ExchangeSetStandard", "S-100");
        catalog.put("ExchangeSetVersion", "1.0");
        catalog.put("ExchangeSetName", exchangeSet.getName());
        catalog.put("Content", exchangeSet.getContentItems()); // Will serialize content items

        xmlMapper.writeValue(outputFile, catalog);
    }
}
