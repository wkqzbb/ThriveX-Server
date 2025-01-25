package liuyuyang.net.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jsonwebtoken.Claims;
import liuyuyang.net.annotation.PremId;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.RolePermissionMapper;
import liuyuyang.net.model.RolePermission;
import liuyuyang.net.properties.JwtProperties;
import liuyuyang.net.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
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
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class PremIdAspect {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    // 定义切点，支持类和方法上的注解
    @Pointcut("@within(liuyuyang.net.annotation.PremId) || @annotation(liuyuyang.net.annotation.PremId)")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        PremId id = getMethodAnnotation(joinPoint);

        if (id != null) {
            String prem = id.value();
            log.info("权限名称：{}", prem);

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                HttpServletResponse response = attributes.getResponse();

                // 获取token
                String token = request.getHeader("Authorization");
                System.out.println("Authorization Header: " + token);

                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                Map<String, Object> role;

                // 解析token
                try {
                    Claims claims = JwtUtils.parseJWT(jwtProperties.getSecretKey(), token);
                    System.out.println(claims);
                    role = (Map<String, Object>) claims.get("roleId");

                    // 通过角色查询每个权限
                    LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(RolePermission::getRoleId, id);
                } catch (Exception e) {
                    response.setStatus(401);
                    throw new CustomException(401, "身份验证失败：无效或过期的token");
                }

                log.info("角色ID：{}", role.get("id"));
            }
        }
    }

    // 获取当前方法上的 @PermName 注解
    private PremId getMethodAnnotation(JoinPoint joinPoint) {
        Method method = getCurrentMethod(joinPoint);
        if (method != null) {
            return method.getAnnotation(PremId.class);
        }
        return null;
    }

    // 获取当前执行的方法对象
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
            log.error("获取方法时出错", e);
        }
        return null;
    }
}