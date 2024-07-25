package hello.hellospring.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hello.hellospring.dto.UserDto;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class MinioServiceTest {

    @Autowired
    private MinioClient minioClient;

    // 버킷과 오브젝트 이름 설정
    private final String bucketName = "json-bucket";
    private final String objectName = "users.json";

    // 버킷 생성 + 파일 업로드
    @Test
    public void uploadFile() {
        try {
            List<List<UserDto>> userList = getUserList();

            // JSON으로 직렬화
            ObjectMapper objectMapper = new ObjectMapper();

            // json 보기 좋게 정렬
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            String jsonString = objectMapper.writeValueAsString(userList);

            // 문자열을 바이트 배열로 변환
            byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);

            // 바이트 배열을 ByteArrayInputStream으로 변환
            ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);

            // 버킷이 존재하지 않으면 생성
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // JSON 데이터를 Minio에 저장
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(bais, jsonBytes.length, -1)
                            .contentType("application/json")
                            .build());

            System.out.println("JSON data successfully saved to Minio.");

        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

    @NotNull
    private List<List<UserDto>> getUserList() {
        // UserDTO 데이터를 생성
        List<List<UserDto>> userList = new ArrayList<>();
        List<UserDto> subList1 = new ArrayList<>();
        List<UserDto> subList2 = new ArrayList<>();

        UserDto user1 = new UserDto();
        user1.setId("1");
        user1.setName("John Doe");
        user1.setEmail("john@example.com");

        UserDto user2 = new UserDto();
        user2.setId("2");
        user2.setName("Jane Doe");
        user2.setEmail("jane@example.com");

        subList1.add(user1);
        subList2.add(user2);

        userList.add(subList1);
        userList.add(subList2);
        return userList;
    }

    @Test
    public void deleteBucket() {
        try {
            // 버킷 내 모든 객체 삭제
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build());

            for (Result<Item> result : results) {
                Item item = result.get();
                minioClient.removeObject(
                        RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
            }

            // 버킷 삭제
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            System.out.println("Bucket " + bucketName + " successfully deleted.");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

    // 버킷 내 특정 파일 삭제 메서드
    @Test
    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            System.out.println("File " + objectName + " in bucket " + bucketName + " successfully deleted.");
        } catch (MinioException e) {
            System.err.println("Error occurred: " + e);
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

    @Test
    public void loadBucketFile() {
        try {
            // Minio에서 오브젝트 조회
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());

            // 오브젝트 내용을 문자열로 변환
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            for (int bytesRead = stream.read(buffer); bytesRead != -1; bytesRead = stream.read(buffer)) {
                baos.write(buffer, 0, bytesRead);
            }

            String jsonContent = baos.toString(StandardCharsets.UTF_8);

            // 스트림 닫기
            stream.close();;

            // 가져온 내용을 출력
            System.out.println("Retrieved JSON content: " + jsonContent);

            // JSON 데이터가 비어 있는지 확인
            if (jsonContent.isEmpty()) {
                System.err.println("Error: Retrieved JSON content is empty.");
            }

            // JSON 데이터를 List<List<UserDTO>>로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<List<UserDto>> userList = objectMapper.readValue(jsonContent, new TypeReference<List<List<UserDto>>>() {});

            // 스트림 닫기
            stream.close();
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

}