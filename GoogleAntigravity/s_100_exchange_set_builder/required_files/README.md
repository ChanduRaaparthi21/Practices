# Required Files for S-100 Exchange Set Builder Testing

This folder contains all the necessary files to test the S-100 Exchange Set Builder application end-to-end.

## 1. Security Files (Keys & Certificates)

These files are required for digital signing of the exchange sets.

*   **`private_key.pem`**: The private key used to sign the datasets and catalogs.
    *   **Usage**: Upload this file when the application asks for a "Private Key".
*   **`certificate.crt`**: The X.509 public key certificate associated with the private key.
    *   **Usage**: Upload this file when the application asks for a "Certificate".
*   **`public_key.pem`**: The public key extracted from the pair (optional, depending on app requirements).
    *   **Usage**: Upload this if the application asks for a "Public Key" separately.

> **Note**: These are self-signed certificates generated for testing purposes only. Do not use them for production.

## 2. Dataset Files

These files represent the hydrographic data you want to package.

*   **`sample_dataset.xml`**: A dummy S-100 dataset file containing sample bathymetric and coastline features.
    *   **Usage**: Upload this file when adding a "Dataset" to an exchange set.
*   **`dataset_metadata.xml`**: A dummy metadata file describing the dataset.
    *   **Usage**: Upload this file if the application requires separate metadata or support files.

## How to Test

1.  **Start the Application**: Run the Spring Boot app.
2.  **Login**: Use `admin` / `admin123`.
3.  **Configure Workspace**: Point to a directory (e.g., create `C:\Temp\s100_workspace`).
4.  **Upload Resources**:
    *   Go to the "Resources" or "Certificates" section.
    *   Upload `private_key.pem` as Private Key.
    *   Upload `certificate.crt` as Certificate.
5.  **Create Exchange Set**:
    *   Create a new Exchange Set (e.g., "Test_Set_001").
    *   Add `sample_dataset.xml` as a dataset.
6.  **Sign & Package**:
    *   Click "Sign" and select the uploaded Private Key.
    *   Click "Package" to generate the ZIP file.
7.  **Verify**: Check the output ZIP file contains the signed catalog and dataset.
