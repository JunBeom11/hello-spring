package hello.hellospring.controller;

import hello.hellospring.domain.BotDto;
import hello.hellospring.service.TestToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestToolController {

    private final TestToolService testToolService;

    // ajax
    @GetMapping("/test-tool")
    public String manageBot() {
        return "/bot/manage-bot";
    }

    // async
    @GetMapping("/test-tool2")
    public String manageBot2() {
        return "/bot/manage-bot2";
    }

    @GetMapping("/test-tool/bot/load")
    @ResponseBody
    public List<BotDto> loadBot() throws IOException {
        return testToolService.loadBot();
    }

    @GetMapping("/bot/list/load")
    @ResponseBody
    public List<BotDto> loadBotList() throws IOException {
        return testToolService.loadBotList();
    }

}
