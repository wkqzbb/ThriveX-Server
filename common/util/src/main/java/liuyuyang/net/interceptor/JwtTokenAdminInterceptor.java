package liuyuyang.net.interceptor;

import io.jsonwebtoken.Claims;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.properties.JwtProperties;
import liuyuyang.net.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());

        // 如果是预检请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查方法上是否有@NoTokenRequired注解，如果有就直接放行
        if (method.isAnnotationPresent(NoTokenRequired.class)) {
            return true;
        }

        // 校验令牌
        try {
            log.info("jwt校验：{}", token);

            // 如果是GET请求没有传token就直接放行，传了token就必须经过验证
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                if (token != null) {
                    if (token.startsWith("Bearer ")) token = token.substring(7);
                    JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
                    return true;
                } else {
                    return true;
                }
            }

            // 处理Authorization的Bearer
            if (token.startsWith("Bearer ")) token = token.substring(7);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);

            // 放行
            return true;
        } catch (Exception ex) {
            System.out.println("校验失败：" + ex);
            // 校验失败，响应401状态码
            response.setStatus(401);
            throw new CustomException(401, "身份验证失败：无效或过期的token");
        }
    }
}
