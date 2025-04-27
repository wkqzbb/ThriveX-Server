package liuyuyang.net.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // @Value("${cors.allowed-origins}")
    // private String[] allowedOrigins;

    private String[] allowedOrigins = {
        "http://localhost:3000",
        "https://liuyuyang.net",
        "https://www.liuyuyang.net",
        "https://blog.liuyuyang.net"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println(registry);

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}