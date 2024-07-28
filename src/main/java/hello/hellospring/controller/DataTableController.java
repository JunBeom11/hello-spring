package hello.hellospring.controller;

import hello.hellospring.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class DataTableController {

    @GetMapping("/datatable")
    public String getDataTable(Model model) {
        List<User> users = Arrays.asList(
                new User(1, "Alice", 35),
                new User(2, "Bob", 30),
                new User(3, "Charlie", 20)
        );
        model.addAttribute("users", users);

        return "datatable";
    }

}
