package hello.hellospring.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Redis {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper; // ObjectMapper 추가

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get2(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }

        // LinkedHashMap을 원하는 객체로 변환
        try {
            String json = objectMapper.writeValueAsString(value);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Redis value to " + clazz.getName(), e);
        }
    }

}
