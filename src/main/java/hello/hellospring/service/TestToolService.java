package hello.hellospring.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.hellospring.domain.BotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestToolService {

    private final ObjectMapper objectMapper;

    public List<BotDto> loadBotList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new ClassPathResource("/json/sample.json").getInputStream();
        JsonNode rootNode = objectMapper.readTree(inputStream);
        List<BotDto> botList = new ArrayList<>();

        for (JsonNode node : rootNode) {
            String name = node.get("name").asText();
            String version = node.get("version").asText();
            String createAt = node.get("createAt").asText(); // JSON의 createAt을 String으로 직접 읽기
            int count = node.get("count").asInt();

            /*BotDto botDto = BotDto.builder()
                            .name(name)
                            .version(version)
                            .createAt(createAt)
                            .count(count)
                            .build();*/
            BotDto botDto = new BotDto();

            botList.add(botDto);
        }

        return botList;
    }

    public List<BotDto> loadBot() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/json/sample.json");
        if (inputStream == null) {
            throw new IOException("Resource not found: /json/sample.json");
        }

        // Read JSON data into a List of BotDto objects
        List<BotDto> botList = objectMapper.readValue(inputStream, new TypeReference<List<BotDto>>() {});
        return botList;
    }
}
