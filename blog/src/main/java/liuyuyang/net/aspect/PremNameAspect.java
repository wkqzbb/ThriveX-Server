package liuyuyang.net.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jsonwebtoken.Claims;
import liuyuyang.net.annotation.PremName;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.PermissionMapper;
import liuyuyang.net.model.Permission;
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
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class PremNameAspect {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private PermissionMapper permissionMapper;

    // 定义切点，支持类和方法上的注解
    @Pointcut("@within(liuyuyang.net.annotation.PremName) || @annotation(liuyuyang.net.annotation.PremName)")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        PremName name = getMethodAnnotation(joinPoint);

        if (name != null) {
            String prem = name.value();
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
                    role = (Map<String, Object>) claims.get("role");

                    // 通过角色查询每个权限
                    LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(Permission::getRoleId, role.get("id")).eq(Permission::getName, prem);
                    List<Permission> permissions = permissionMapper.selectList(lambdaQueryWrapper);

                    if(permissions == null || permissions.isEmpty()) throw new CustomException("暂无权限，请联系管理员");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(401);
                    throw new CustomException(401, e.getMessage());
                }

                log.info("角色ID：{}", role.get("id"));
            }
        }
    }

    // 获取当前方法上的 @PermName 注解
    private PremName getMethodAnnotation(JoinPoint joinPoint) {
        Method method = getCurrentMethod(joinPoint);
        if (method != null) {
            return method.getAnnotation(PremName.class);
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