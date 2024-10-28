package liuyuyang.net.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping
    public String Home() {
        System.out.println(123);
        return "<h1>Hello ThriveX</h1>";
    }
}
