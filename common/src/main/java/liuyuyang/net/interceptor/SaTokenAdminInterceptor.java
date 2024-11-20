package liuyuyang.net.interceptor;

import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.execption.CustomException;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Sa-Token令牌校验的拦截器
 */
@Component
@Slf4j
public class SaTokenAdminInterceptor implements HandlerInterceptor {

    /**
     * 校验Sa-Token
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是预检请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 检查方法上是否有@NoTokenRequired注解，如果有就直接放行
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(NoTokenRequired.class)) {
                return true;
            }
        }

        // 校验Sa-Token
        try {
            // 如果需要对GET请求进行特殊处理，保持逻辑一致
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                if (StpUtil.isLogin()) {
                    return true;
                } else {
                    // 可选：允许未登录的GET请求
                    return true;
                }
            }

            // 检查登录状态
            StpUtil.checkLogin();

            // 放行
            return true;
        } catch (Exception ex) {
            log.error("校验失败：", ex);
            // 校验失败，响应401状态码
            response.setStatus(401);
            throw new CustomException(401, "身份验证失败：无效或过期的token");
        }
    }
}
