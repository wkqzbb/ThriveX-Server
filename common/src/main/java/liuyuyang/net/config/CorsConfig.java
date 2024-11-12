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
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                // .allowedOriginPatterns(domainWhitelist)
                // .allowedOriginPatterns("http://*.liuyuyang.net", "https://*.liuyuyang.net")
                .allowedMethods("*") // 支持方法
                .allowedHeaders("*");
    }
}