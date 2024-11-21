package liuyuyang.net.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import io.jsonwebtoken.Claims;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.properties.JwtProperties;
import liuyuyang.net.utils.JwtUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class CheckRoleAspect {
    @Resource
    private JwtProperties jwtProperties;

    // 定义切点，支持类和方法上的注解
    @Pointcut("@within(liuyuyang.net.annotation.CheckRole) || @annotation(liuyuyang.net.annotation.CheckRole)")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        CheckRole checkRole = getCheckRoleAnnotation(joinPoint);

        if (checkRole != null) {
            String[] roles = checkRole.value();
            System.out.println("自定义注解前置通知！角色：" + String.join(", ", roles));

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                HttpServletResponse response = attributes.getResponse();

                String token = request.getHeader("Authorization");
                System.out.println("Authorization Header: " + token);

                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                Claims claims = JwtUtils.parseJWT(jwtProperties.getSecretKey(), token);
                Map<String, Object> role = (Map<String, Object>) claims.get("role");

                boolean isPerm = Arrays.asList(roles).contains(role.get("mark"));

                if (!isPerm) {
                    response.setStatus(401);
                    throw new CustomException(401, "该权限只有" + String.join(", ", roles) + "可以访问");
                }
            }
        }
    }

    private CheckRole getCheckRoleAnnotation(JoinPoint joinPoint) {
        Method method = getCurrentMethod(joinPoint);
        if (method != null) {
            CheckRole checkRole = method.getAnnotation(CheckRole.class);
            if (checkRole != null) {
                return checkRole;
            }
        }
        return joinPoint.getTarget().getClass().getAnnotation(CheckRole.class);
    }

    private Method getCurrentMethod(JoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Class<?> targetClass = joinPoint.getTarget().getClass();
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}