package hello.hellospring.controller;

import hello.hellospring.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class DataTableController {

    @GetMapping("/datatable")
    public String getDataTable(Model model) {
        List list = new ArrayList();

        List<User> users = Arrays.asList(
                new User(1, "Alice", 35, new ArrayList()),
                new User(2, "Bob", 30, list),
                new User(3, "Charlie", 20, list)
        );
        model.addAttribute("users", users);

        return "datatable/datatable";
    }

    @GetMapping("/datatable2")
    public String getDataTable2() {
        return "datatable/datatable2";
    }

    @GetMapping("/api/users")
    @ResponseBody
    public List<User> getUsers() {
        return Arrays.asList(
                new User(1, "Alice", 111, new ArrayList()),
                new User(2, "Bob", 50, new ArrayList()),
                new User(3, "Charlie", 5, new ArrayList())
        );
    }

}
