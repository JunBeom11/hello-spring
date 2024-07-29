package hello.hellospring.controller;

import hello.hellospring.domain.UserListWrapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PopupController {

    @PostMapping("/popup")
    public String popup(@Valid UserListWrapper users, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "error/errorPage";  // 유효성 검증 실패 시 표시할 페이지
        }

        model.addAttribute("users", users.getUsers());

        return "modal/popup";
    }
}
