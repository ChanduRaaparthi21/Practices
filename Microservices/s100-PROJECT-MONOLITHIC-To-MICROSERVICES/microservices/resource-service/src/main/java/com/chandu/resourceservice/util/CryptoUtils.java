package com.chandu.resourceservice.util;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

@Component
public class CryptoUtils {

    public void validateCertificate(byte[] certData) throws Exception {
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        InputStream is = new ByteArrayInputStream(certData);
        X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
        cer.checkValidity(new Date()); // Checks if current date is within validity period
    }

    // Placeholder for revocation check - would typically require CRL or OCSP
    public void checkRevocation(X509Certificate cert) {
        // Implementation for revocation check
    }
}
