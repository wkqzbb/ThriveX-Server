package liuyuyang.net.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowedOrigins("*")
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 支持方法
                .allowedHeaders("*");
    }
}