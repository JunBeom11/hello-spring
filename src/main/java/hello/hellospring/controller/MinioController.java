package hello.hellospring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.hellospring.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MinioService minioService;

    @GetMapping("/upload")
    public String uploadJsonToMinio() {
        String bucketName = "json-bucket";
        String objectName = "sampleJson3.json";
        String resourcePath = "json/sample.json"; // resources 폴더 내의 경로

        minioService.uploadJsonFromResources(bucketName, objectName, resourcePath);
        return "JSON 데이터가 성공적으로 MinIO에 저장되었습니다.";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFileFromMinio(@RequestParam(name = "bucketName") String bucketName,
                                                        @RequestParam(name = "objectName") String objectName) {
        // 버킷 존재 여부 확인
        if (!minioService.bucketExists(bucketName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("버킷 '" + bucketName + "'가 존재하지 않습니다.").getBytes(StandardCharsets.UTF_8));
        }

        // 객체 존재 여부 확인
        if (!minioService.objectExists(bucketName, objectName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("객체 '" + objectName + "'가 존재하지 않습니다.").getBytes(StandardCharsets.UTF_8));
        }

        // 파일 다운로드
        byte[] fileContent = minioService.downloadFile(bucketName, objectName);
        if (fileContent == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("파일 다운로드 중 오류가 발생했습니다.").getBytes(StandardCharsets.UTF_8));
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + objectName + "\"")
                .body(fileContent);
    }

}
