package hello.hellospring.controller;

import hello.hellospring.domain.User;
import hello.hellospring.utils.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private Redis redis;

    @PostMapping("/save")
    public void save(@RequestParam("key") String key, @RequestParam("value") String value) {
        redis.save(key, value);
    }

    @PostMapping("/save2")
    public void save2(@RequestParam("path") String path, @RequestParam("key") String key, @RequestParam("value") String value) {
        String fullKey = path + ":" + key;
        redis.save(fullKey, value);
    }

    @PostMapping("/save3")
    public void save3(@RequestParam("path") String path, @RequestParam("key") String key, @RequestBody User user) {
        String fullKey = path + ":" + key;
        redis.save(fullKey, user);
    }

    @GetMapping("/get")
    public Object get(@RequestParam("key") String key) {
        return redis.get(key);
    }

    @GetMapping("/get2")
    public Object get2(@RequestParam("path") String path, @RequestParam("key") String key) {
        String fullKey = path + ":" + key;
        return redis.get(fullKey);
    }

    @GetMapping("/get3")
    public User get3(@RequestParam("path") String path, @RequestParam("key") String key) {
        String fullKey = path + ":" + key;
        User user =  redis.get2(fullKey, User.class);

        return user;
    }

}
