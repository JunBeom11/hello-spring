package hello.hellospring.controller;

import hello.hellospring.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MemberRestController {

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(value = "name", required = false) String name) {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "이준범", 29, null));
        users.add(new User(2, "김준범", 19, null));
        users.add(new User(3, "박준범", 39, null));

        if (name != null) {
            users = users.stream().filter(user -> user.getUserName().contentEquals(name)).collect(Collectors.toList());
        }

        return users;
    }

}
