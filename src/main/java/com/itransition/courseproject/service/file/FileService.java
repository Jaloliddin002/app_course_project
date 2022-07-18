package com.itransition.courseproject.service.file;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;
import com.itransition.courseproject.exception.resource.FileProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${firebase.bucket.name}")
    private String bucketName;
    @Value("${firebase.image.url}")
    private String fileUrl;

    public String uploadFile(MultipartFile file) {
        try{
            String fileName = generateFileName(file.getOriginalFilename());
            StorageOptions
                    .newBuilder()
                    .setCredentials(getCredentials())
                    .build()
                    .getService()
                    .create(getBlobInfo(fileName, file.getContentType()), file.getBytes());
            return String.format(fileUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        }catch (IOException ex){
            throw new FileProcessingException("File upload error", "Ошибка загрузки файла");
        }
    }

    public boolean delete(String path) {
        try {
            String fileUniqueId = this.getFileNameFromPath(path);
            this.deleteFile(fileUniqueId);
            return true;
        } catch (Exception e) {
            throw new FileProcessingException("File deletion error", "Ошибка удаления файла");
        }
    }

    private void deleteFile(String fileUniqueId){
        StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .delete(BlobId.of(bucketName, fileUniqueId));
    }


    private BlobInfo getBlobInfo(String fileUniqueId, String contentType) {
        return BlobInfo
                .newBuilder(BlobId.of(bucketName, fileUniqueId))
                .setContentType(Objects.isNull(contentType) ? "media" : contentType)
                .build();
    }

    private String getFileNameFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("?"));
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID() + originalFileName;
    }

    private Credentials getCredentials(){
        try{
            String credentials = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"collectin-3959d\",\n" +
                    "  \"private_key_id\": \"5965fe39064e89f0a53ca89a1b37168c81ed2ca3\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCu95PXJMCxm9KD\\nToSgciHMcdiWwOLQ6B6HzjnKx0cVoQurhs5iURMP6z61aJzxT1EqhQOR1KxpYFIf\\nY5FOtg+eHz2mA1TsdqFZzbgj60XfqJX7V8D4etcoEDvpNCqfEYQd9AiKtg4DsHZ6\\nRXcCLGOoMHesSdQWU6Dh+E1QzdOcErqZwkBUVrP0zJQYeX+jDSeh+szmoumrzBbI\\nkZvwRC35Qgk3o6UVZqfFUuq2tmGuqPTL4qwf5GWPeAgYkG4fMs1xIJNQBoPhGiiz\\nkTe0/BfgZsRs/WOChCskFVvO27t1snTN+nVjv+jpQjONsRWe7WrKN6fUG3u0dDKo\\nKHN2ZDavAgMBAAECggEABTFEW3e0EEKGCgx3tjjKaxD5jhEA5UaM/HopXsBwxSbw\\nNvQqI871/T7z1wF2i88UBZHIrVYFMHezAg/FxgavT1pOnDZXj8bH9iKdtebfFsC9\\ncEN8lkWqEUOKEmULeJxpshijmrfFpcb/LlnCA59rxbVoDfIczTCDtDrqhCuwLsE0\\nDfgzlgx0ErgHDCnqVuh7vNVHv7MdTenBr4Fn0WliNOs/igPC86YwmL3fOTphusD0\\n1iLj+viyrqVceSvw466hfzA8c1SZfgtTYYZeUAMDWLa3jGJ14VoKitVn2DHeOY4Y\\nsb4IY6CSab8ShEviwGFYq3WM+XX2z0CVlvIUK1pl4QKBgQD29fqwTgPCD7riGzqL\\npTB/9Tr9AC9IT+JKLZP8XQ70R2z2pvqRqgth8ZJTQIMmyBzkUDIpZl7ezHDDCfgm\\nwTL/fzTSx8eLfr08dwF76q4ccTK0iheo+x9TxZKUWitUVOFg2NH0xcd4pXyM+J/+\\nhEc5VMISrqx2B41HJKqrRGbefQKBgQC1XwR44vZdxieJBMYOKu5pApi77R8tZN2p\\nXcsgw3J4kks7Q2XMvlpEtlkzLk3QUTwhiBPxXk63AKnfEZ5I9K58yWU35usG8LGK\\nw0blRdt+CsaECW6QMNC/PNJfmxm/p+wzSQoCSCVojFB9hHg09UFALP8F01vKJHTi\\nBHyaPexVmwKBgDSNX41I7K/NHR2kYa75tfpkuuq/sVAX/7V9QXKAcwA0etF58vQw\\nVT/AYjzpC3+jMdK75hC9Qg7zPkOiyXWlwy5vaVQ5QxNCIw7M3Dn8VJSk2f9lK2fA\\nGIiBEJIVJgdKELPFL9iAhdP8kyaEebd1epqOmMXU+cocDUpIWTEc2y8ZAoGAP/m1\\nLSL5vhggZ+98IRE5qWbWEyvQHIYAvjYsaXEnlqobyVq7tN3MH4Kmsiv6HKoa6MUE\\nK9aVM24TfXDMgXV1euTbRD2612bdaVH6VvMuOiZ44agmDMx7RYmht0GRco47/dcc\\npbNnJpqf5bZtZFjCXG3Cwv5L2WDErdjhTtLa41MCgYEA9EHgXEyUH2w/O1T71s9I\\n7R/BKyWEnl5SECU4inanTWP1MgqxHM66PcGnFKRwKlwgOeibF6Wv6o0JSMflpgmV\\nOscddsUDhZi1QCq66NgoxLx6e2hxs7k2CA7QVrFrgxP0EVJ9GSKkqNLtSQWscyiA\\nGtg1jMZQ9wnMor+ZfCy4zxo=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-n4en0@collectin-3959d.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"110000901445250151404\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-n4en0%40collectin-3959d.iam.gserviceaccount.com\"\n" +
                    "}\n";
            return GoogleCredentials.fromStream(new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8)));
        } catch(IOException ex){
            throw new FileProcessingException("File upload error", "Ошибка загрузки файла");
        }
    }
}
