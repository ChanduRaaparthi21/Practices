package com.chandu.s_100_exchange_set_builder.util;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.Signature;

@Component
public class SignatureUtils {

    public void signFile(File fileToSign, File privateKeyFile, File signatureFile) throws Exception {
        PrivateKey privateKey = readPrivateKey(privateKeyFile);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        byte[] data = Files.readAllBytes(fileToSign.toPath());
        signature.update(data);

        byte[] digitalSignature = signature.sign();

        Files.write(signatureFile.toPath(), digitalSignature);
    }

    private PrivateKey readPrivateKey(File file) throws IOException {
        try (FileReader keyReader = new FileReader(file);
                PEMParser pemParser = new PEMParser(keyReader)) {

            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

            if (object instanceof PEMKeyPair) {
                return converter.getKeyPair((PEMKeyPair) object).getPrivate();
            } else {
                throw new IllegalArgumentException("Invalid private key format");
            }
        }
    }
}
