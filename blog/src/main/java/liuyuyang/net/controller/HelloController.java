package liuyuyang.net.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = "首页")
@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping
    public String Home() {
        return "<h1>Hello ThriveX</h1>";
    }
}
