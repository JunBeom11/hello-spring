package hello.hellospring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private ObjectMapper objectMapper;

    public void uploadJsonFromResources(String bucketName, String objectName, String resourcePath) {
        try {
            // 리소스에서 JSON 파일 읽기
            ClassPathResource resource = new ClassPathResource(resourcePath);
            InputStream inputStream = resource.getInputStream();

            // JSON 데이터를 Map으로 변환 (필요시)
            Map jsonData = objectMapper.readValue(inputStream, Map.class);
            inputStream.close();

            // JSON 데이터를 문자열로 변환
            String jsonString = objectMapper.writeValueAsString(jsonData);
            inputStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));

            // 버킷이 존재하지 않으면 생성
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // MinIO에 파일 업로드
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType("application/json")
                            .build()
            );
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean objectExists(String bucketName, String objectName) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            boolean exists = inputStream != null;
            if (inputStream != null) {
                inputStream.close();
            }
            return exists;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            return false;
        }
    }

    public byte[] downloadFile(String bucketName, String objectName) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return StreamUtils.copyToByteArray(inputStream);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
