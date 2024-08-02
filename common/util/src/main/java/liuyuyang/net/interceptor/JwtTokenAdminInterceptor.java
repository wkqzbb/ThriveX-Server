package liuyuyang.net.interceptor;

import io.jsonwebtoken.Claims;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.properties.JwtProperties;
import liuyuyang.net.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是GET请求，直接放行
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());

        // 校验令牌
        try {
            log.info("jwt校验：{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            System.out.println("校验成功");

            // 放行
            return true;
        } catch (Exception ex) {
            System.out.println("校验失败：" + ex.getMessage());
            // 校验失败，响应401状态码
            response.setStatus(401);
            throw new YuYangException(401, "身份验证失败");
        }
    }
}
