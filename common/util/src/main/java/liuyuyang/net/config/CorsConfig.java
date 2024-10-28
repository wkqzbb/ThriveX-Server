package liuyuyang.net.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${cors.domainWhitelist}")
    private String domainWhitelist;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println(domainWhitelist);
        System.out.println(8888);
        registry.addMapping("/**") // 所有接口
                // .allowedOriginPatterns(domainWhitelist) // 支持域
                .allowedOriginPatterns("http://*.liuyuyang.net", "https://*.liuyuyang.net") // 支持域
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 支持方法
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Access-Control-Allow-Origin");
    }
}