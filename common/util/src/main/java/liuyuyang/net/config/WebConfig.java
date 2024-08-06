package liuyuyang.net.config;

import liuyuyang.net.interceptor.JwtTokenAdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    private static final Set<String> EXCLUDED_PATHS = new HashSet<>(Arrays.asList(
            "/doc.html",
            "/swagger-resources",
            "/webjars",
            "/v2/api-docs",
            "/swagger-ui.html"
    ));

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> {
            RequestMapping requestMapping = c.getAnnotation(RequestMapping.class);
            if (requestMapping != null && requestMapping.value().length > 0) {
                String path = requestMapping.value()[0];
                return !EXCLUDED_PATHS.contains(path);
            }
            return true;
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register");
    }
}
