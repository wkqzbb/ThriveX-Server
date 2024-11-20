package liuyuyang.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("服务已启动");
        SpringApplication.run(Main.class, args);
    }
}